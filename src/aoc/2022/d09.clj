(ns aoc.2022.d09
  (:require
   [aoc.coll-util :refer [x-nth]]
   [aoc.file-util :as f]
   [aoc.grid :as grid]
   [aoc.math-util :refer [clamp-1]]))

(def input (f/read-lines "2022/d09.txt"))

(defn touching? [a b]
  (->> (grid/vector-diff a b)
       (map #(Math/abs %))
       (every? #(<= % 1))))

(defn step-tail [t h]
  (if (touching? t h)
    t
    (grid/vector-add t (map clamp-1 (grid/vector-diff h t)))))

(defn solve [knots input]
  (->> (grid/moves->path input)
       (iterate (partial reductions step-tail grid/origin))
       (x-nth knots)
       set
       count))

(defn part-1 [input] (solve 1 input))
(defn part-2 [input] (solve 9 input))
