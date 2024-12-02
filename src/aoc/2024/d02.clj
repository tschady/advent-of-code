(ns aoc.2024.d02
  (:require
   [aoc.coll-util :as c]
   [aoc.file-util :as f]
   [aoc.string-util :as s]))

(def input (map s/ints (f/read-lines "2024/d02.txt")))

(defn safe? [report]
  (let [deltas (c/intervals report)]
    (or (every? #{-3 -2 -1} deltas)
        (every? #{3 2 1} deltas))))

(defn dampened-candidates [report]
  (map (partial c/list-remove report) (range (count report))))

(defn safe-with-dampening? [report]
  (some safe? (cons report (dampened-candidates report))))

(defn part-1 [input] (c/count-truthy (map safe? input)))

(defn part-2 [input] (c/count-truthy (map safe-with-dampening? input)))
