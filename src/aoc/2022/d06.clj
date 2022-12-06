(ns aoc.2022.d06
  (:require
   [aoc.file-util :as f]))

(def input (f/read-file "2022/d06.txt"))

(defn find-marker [n s]
  (->> s
       (partition n 1)
       (keep-indexed (fn [i xs] (when (apply distinct? xs) i)))
       first
       (+ n)))

(defn part-1 [input] (find-marker 4 input))

(defn part-2 [input] (find-marker 14 input))
