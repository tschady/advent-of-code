(ns aoc.2017.d13
  (:require
   [aoc.file-util :as f]
   [medley.core :as medley]))

(def input (f/read-int-vectors "2017/d13.txt"))

(defn catch? [delay [dep rng]]
  (zero? (mod (+ delay dep) (* 2 (dec rng)))))

(defn part-1 [input]
  (->> input
       (filter (partial catch? 0))
       (map (partial reduce *))
       (reduce + 0)))

(defn part-2 [input]
  (->> (range)
       (map #(some (partial catch? %) input))
       (medley/take-upto nil?)
       count
       dec))
