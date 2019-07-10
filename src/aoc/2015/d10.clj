(ns aoc.2015.d10)

(def input "3113322113")

(defn look-and-say [s]
  (->> s
       (partition-by identity)
       (mapcat (juxt count first))
       (apply str)))

(defn solve [input n]
  (-> (iterate look-and-say input)
      (nth n)
      count))

(defn part-1 [input] (solve input 40))

(defn part-2 [input] (solve input 50))
