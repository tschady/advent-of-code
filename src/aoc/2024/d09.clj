(ns aoc.2024.d09
  (:require
   [aoc.file-util :as f]
   [aoc.math-util :as m :refer [series-sum]]
   [aoc.string-util :as s]))

(def input (f/read-file "2024/d09.txt"))

(defn parse [s]
  (->> (str s "0")
       s/explode-digits
       (partition 2)
       (map-indexed (fn [i x] [{:id i :len (first x)}
                               (when (pos? (second x)) {:id nil :len (second x)})]))
       (apply concat)
       (remove nil?)
       (apply vector)))

(defn pack [diskmap]
  (loop [packed   []
         unpacked diskmap]
    (let [curr (first unpacked)
          end  (peek unpacked)]
      (cond
        (nil? curr)         packed
        (zero? (:len curr)) (recur packed (rest unpacked))
        (some? (:id curr))  (recur (conj packed curr) (subvec unpacked 1))
        (nil? (:id end))    (recur packed (pop unpacked))

        (<= (:len curr) (:len end)) ; have more at the end than necessary to fill this spot
        (recur (conj packed (assoc curr :id (:id end)))
               (conj (subvec (pop unpacked) 1) (assoc end :len (- (:len end) (:len curr)))))

        :else ; have less at the end than necessary to fill
        (recur (conj packed (assoc curr :id (:id end) :len (:len end)))
               (into [(assoc curr :len (- (:len curr) (:len end)))] (subvec (pop unpacked) 1)))))))

(defn find-first-earlier-empty [diskmap size loc]
  (first (keep-indexed (fn [i {:keys [id len]}]
                         (when (and (nil? id) (<= size len) (< i loc))
                           i))
                       diskmap)))

(defn pack2 [diskmap]
  (reduce (fn [packed {:keys [id len] :as entry}]
            (let [i (.indexOf packed entry)
                  i' (find-first-earlier-empty packed len i)]
              (if (nil? i')
                packed
                (let [extra-space (- (:len (get packed i')) len)]
                  (-> (vec (concat (conj (subvec packed 0 i') entry {:id nil :len extra-space}) (subvec packed (inc i'))))
                      (assoc-in [(inc i) :id] nil))))))
          diskmap
          (reverse (remove #(nil? (:id %)) diskmap))))

(defn checksum [packed]
  (:sum (reduce (fn [{:keys [i sum] :as acc} {:keys [id len]}]
                  (-> acc
                      (update :sum + (* (or id 0) (series-sum i (dec (+ len i)))))
                      (update :i + len)))
                {:sum 0 :i 0}
                packed)))

(defn part-1 [input] (-> input parse pack checksum))

(defn part-2 [input] (-> input parse pack2 checksum))
