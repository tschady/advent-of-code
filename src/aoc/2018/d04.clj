(ns aoc.2018.d04
  (:require [aoc.file-util :as file-util]
            [clojure.string :as str]))

(def input (file-util/read-lines "2018/d04.txt"))

;; Format: '[yyyy-MM-dd hh:mm] message'.
;; The first word of msg is sufficient.  Optional guard id after '#'
;; e.g. "[1518-11-01 00:00] Guard #10 begins shift"
(def log-pattern #"^\[.*(\d\d)\] (.*?) (?:#(\d+))?")

(defn mode
  "Returns frequency vector of most common element and its count."
  [coll]
  (apply max-key val (frequencies coll)))

(defn build-sleep-log
  "Transform raw log lines into sleep log: a vector of sleep entries,
  where each entry is a map of :guard, :start: end keys showing
  sleep times in clock minutes between midnight and 1am."
  [log-lines]
  ;; rows have context - the guard ID of each row is the last guard id
  ;;  mentioned.  Store these temporary vars in the top level accumulator,
  ;;  discarding them at the end.
  (:log (reduce (fn [acc line]
                  (let [[_ mm msg id] (re-find log-pattern line)
                        minutes (Integer/parseInt mm)]
                    (condp str/starts-with? msg
                      "Guard" (assoc acc :guard (Integer/parseInt id))
                      "falls" (assoc acc :start minutes)
                      "wakes" (update acc :log conj
                                      (-> (select-keys acc [:guard :start])
                                          (assoc :end minutes))))))
                {:log []}
                (sort log-lines))))

(defn build-sleep-map
  "Return map of guard id to the list of minutes they've slept.
  e.g. If guard #10 slept on 0:20 two nights, returns {10 (20 20)}"
  [sleep-log]
  (reduce (fn [acc {:keys [guard start end]}]
            (update acc guard into (range start end)))
          {}
          sleep-log))

(defn sleepiest-guard-by-total
  "Returns guard ID and minutes slept of the sleepingest guard."
  [sleep-map]
  (apply max-key (comp count val) sleep-map))

(defn sleepiest-guard-by-minute
  "Returns [guard [minute freq]] of the given sleep map, with the guard
  who has the most occurrences of any one minute slept."
  [sleep-map]
  (->> sleep-map
       (map (fn [[k v]] [k (mode v)]))
       (apply max-key (comp val second))))

(defn part-1
  "Return the product of the guard id and minutes slept of the sleepiest
  guard by total minutes slept."
  [input]
  (let [sleep-map (-> input build-sleep-log build-sleep-map)
        [guard minutes] (sleepiest-guard-by-total sleep-map)
        mode-minute (key (mode minutes))]
    (* guard mode-minute)))

(defn part-2
  "Return the product of the guard id and the minute most slept by the
  slepiest guard by the most frequently slept minute."
  [input]
  (let [sleep-map (-> input build-sleep-log build-sleep-map)
        [guard [minute _]] (sleepiest-guard-by-minute sleep-map)]
    (* guard minute)))
