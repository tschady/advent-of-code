(ns aoc.2020.d09
  (:require [aoc.file-util :as file-util]
            [clojure.math.combinatorics :as combo]))

(def input (vec (file-util/read-values "2020/d09.txt")))

(defn valid?
  "Returns true if there are any two values from `window` that sum to `n`"
  [window n]
  (some #{n} (map (partial apply +) (combo/combinations window 2))))

(defn encryption-weakness
  "Returns the sum of the first and last elements of the contiguous range
  subset of `xs` that sums to `target`."
  [xs target]
  (loop [start 0 end 1]
    (when (<= 0 start end (count xs))
      (let [candidate (subvec xs start end)
            sum (reduce + candidate)]
        (cond
          (= sum target) (+ (apply min candidate) (apply max candidate))
          (> sum target) (recur (inc start) end)
          (< sum target) (recur start (inc end)))))))

(defn first-invalid
  "Return the first number of `xs` that is not the sum of two addends from the previous
  `window-size` elements of `xs`."
  [xs window-size]
  (reduce (fn [window n]
            (if (valid? window n)
              (conj (subvec window 1) n)
              (reduced n)))
          (subvec xs 0 window-size)
          (subvec xs window-size)))

(defn part-1 [input window-size]
  (first-invalid input window-size))

(defn part-2 [input window-size]
  (encryption-weakness input (first-invalid input window-size)))
