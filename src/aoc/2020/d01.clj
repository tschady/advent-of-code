(ns aoc.2020.d01
  (:require [aoc.file-util :as file-util]
            [clojure.math.combinatorics :as combo]))

(def input (sort (file-util/read-ints "2020/d01.txt")))

(defn solve
  "Returns the product of the `n` numbers from set of `input` that sum to `target-sum`"
  [input target-sum n]
  (->> (combo/combinations input n)
       (some #(when (= target-sum (apply + %)) %))
       (reduce *)))

(defn part-1 [input] (solve input 2020 2))

(defn part-2 [input] (solve input 2020 3))
