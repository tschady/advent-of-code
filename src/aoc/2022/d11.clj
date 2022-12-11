(ns aoc.2022.d11
  (:require
   [aoc.coll-util :refer [x-nth]]
   [aoc.file-util :as f]
   [aoc.string-util :as s]
   [clojure.math.numeric-tower :refer [lcm]]
   [clojure.string :as str])
  (:import
   (clojure.lang PersistentQueue)))

(def input (f/read-chunks "2022/d11.txt"))

(defn- parse-op [s]
  (let [[_ _ _ _ _ a1 op a2] (str/split s #" ")]
    (eval (read-string (format "(fn [old] (%s' %s %s))" op a1 a2)))))

(defn parse-monkey [chunk]
  (let [[n items op test t-dest f-dest] (str/split-lines chunk)
        [n div t-dest f-dest] (s/ints (str n test t-dest f-dest))]
    {n {:items (reduce conj PersistentQueue/EMPTY (s/ints items))
        :worry (parse-op op)
        :div div
        :dest #(if (zero? (mod % div)) t-dest f-dest)
        :actions 0}}))

(defn build-state [input] (apply merge (map parse-monkey input)))

(defn update-monkey [relief state n]
  (loop [state state]
    (let [monkey (get state n)
          curr   (peek (:items monkey))]
      (if (nil? curr)
        state
        (let [new-item ((comp relief (:worry monkey)) curr)
              dest     ((:dest monkey) new-item)]
          (recur (-> state
                     (update-in [n :items] pop)
                     (update-in [n :actions] inc)
                     (update-in [dest :items] conj new-item))))))))

(defn play-round [relief state]
  (reduce (partial update-monkey relief) state (keys state)))

(defn solve [input relief n]
  (->> (build-state input)
       (iterate (partial play-round relief))
       (x-nth n)
       (vals)
       (map :actions)
       (sort >)
       (take 2)
       (apply *)))

(defn part-1 [input] (solve input #(quot % 3) 20))

(defn part-2 [input]
  ;; Since all our destination checks are `mod` operations, we don't care
  ;; about any values over the least common multiple of all the monkey's
  ;; divisors.
  (let [div (reduce lcm (map :div (vals (build-state input))))]
    (solve input #(rem % div) 10000)))
