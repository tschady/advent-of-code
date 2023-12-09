(ns aoc.2023.d09
  (:require
   [aoc.coll-util :refer [intervals]]
   [aoc.file-util :as f]))

(def input (f/read-int-vectors "2023/d09.txt"))

(defn extrapolate [v]
  (if (every? zero? v)
    0
    (+ (last v) (extrapolate (intervals v)))))

(defn part-1 [input]
  (transduce (map extrapolate) + input))

(defn part-2 [input]
  (transduce (comp (map reverse) (map extrapolate)) + input))
