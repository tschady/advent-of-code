(ns aoc.coll-util)

(defn idx-of-max
  "Return the index of the maximum value of the collection."
  [coll]
  (->> coll
       (map-indexed vector)
       (apply max-key second)
       first))
