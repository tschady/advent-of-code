(ns aoc.2023.d04
  (:require [aoc.file-util :as f]))

(def input (f/read-lines "2023/d04.txt"))

(defn num-winners [card]
  (count (re-seq #"(?<=:.*)(?=\b(\d+)\b.*\|.*\b\1\b)" card)))

(defn score [n] (int (Math/pow 2 (dec n))))

(defn part-1 [input]
  (transduce (comp (map num-winners) (map score)) + input))

(defn totals
  ([xs] (reduce + xs))
  ([xs x] (conj xs (reduce + 1 (take x xs)))))

(defn part-2 [input]
  (transduce (map num-winners) totals '() (reverse input)))
