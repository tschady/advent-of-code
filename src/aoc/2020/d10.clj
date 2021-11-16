(ns aoc.2020.d10
  (:require [aoc.coll-util :as coll-util]
            [aoc.file-util :as file-util]
            [clojure.math.combinatorics :as combo]))

(def input (file-util/read-ints "2020/d10.txt"))

(defn joltage-intervals
  "Return the collection of intervals between each successive adapter,
  after adding in the first (0) and last (max + 3) adapters and sorting."
  [input]
  (-> input
      (conj 0 (+ 3 (apply max input)))
      sort
      coll-util/intervals))

(defn part-1 [input]
  (apply * (-> (joltage-intervals input)
               frequencies
               (select-keys [1 3])
               vals)))

(defn permuted-partition-count
  "Returns the number of different ways a sequence can be carved up
  into segments of length 1,2, or 3"
  [xs]
  (->> (combo/partitions xs :min 1 :max 3)
       (map (comp count combo/permutations))
       (reduce +)))

;;; This algorithm works by finding all the contiguous runs of 1
;;;  and figuring out how many ways we have of navigating them
;;;  with step size 1, 2, or 3.  Since the intervals of 3 have
;;;  only 1 way through, the total pathway count is the product
;;;  of the possibilities through each run of 1.

(defn part-2 [input]
  (->> (joltage-intervals input)
       (partition-by identity)
       (filter #(some #{1} %))
       (map permuted-partition-count)
       (reduce *)))
