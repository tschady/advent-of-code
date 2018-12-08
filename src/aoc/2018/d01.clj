(ns aoc.2018.d01
  (:require [aoc.file-util :as file-util]))

(def input (file-util/read-values "2018/d01.txt"))

(defn part-1
  "Return the sum of a given a collection `coll` of integers."
  [coll]
  (apply + coll))

(defn part-2
  "Return the first duplicated result after adding each element of `coll`.
  Cycle coll until duplicate is reached."
  [coll]
  (reduce
   (fn [[seen sum] delta] (let [cur (+ sum delta)]
                            (if (contains? seen cur)
                              (reduced cur)
                              [(conj seen cur) cur])))
   [#{0} 0]
   (cycle coll)))
