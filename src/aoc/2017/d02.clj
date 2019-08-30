(ns aoc.2017.d02
  (:require [aoc.file-util :as file-util]))

(def input-str (file-util/read-tsv "2017/d02.tsv"))
(def input (map (fn [row] (map #(Integer/parseInt %) row)) input-str))

(defn- max-diff
  "Return the maximum difference between elements of a collection."
  [coll]
  (- (apply max coll) (apply min coll)))

(defn- max-quotient
  "Return the greatest integer quotient between elements of a collection."
  [coll]
  (apply max
         (for [x coll
               y coll
               :when (and (not= x y)
                          (zero? (mod x y)))]
           (/ x y))))

(defn solve- [f coll] (reduce + (map f coll)))

(defn part-1 [coll] (solve max-diff coll))
(defn part-2 [coll] (solve max-quotient coll))
