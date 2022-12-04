(ns aoc.2022.d04
  (:require [aoc.file-util :as f]))

(def input (f/read-ranges "2022/d04.txt"))

(defn fully-contains? [[lo1 hi1 lo2 hi2]]
  (or (>= hi2 hi1 lo1 lo2)
      (>= hi1 hi2 lo2 lo1)))

(defn overlaps? [[lo1 hi1 lo2 hi2]]
  (and (>= hi2 lo1)
       (>= hi1 lo2)))

(defn part-1 [input] (count (filter fully-contains? input)))

(defn part-2 [input] (count (filter overlaps? input)))
