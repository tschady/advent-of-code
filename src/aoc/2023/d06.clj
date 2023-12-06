(ns aoc.2023.d06
  (:require
   [aoc.file-util :as f]
   [aoc.math-util :refer [quadratic]]
   [aoc.string-util :refer [s->int]]))

(def input (f/read-int-vectors "2023/d06.txt"))

(defn combos [[t d]]
  (let [[hi lo] (quadratic 1 (* -1 t) d)]
    (int (inc (- (Math/floor hi) (Math/ceil lo))))))

(defn part-1 [input]
  (reduce * (map combos (apply map list input))))

(defn part-2 [input]
  (combos (map s->int (map (partial apply str) input))))
