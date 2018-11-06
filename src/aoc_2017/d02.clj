(ns aoc-2017.d02
  (:require [aoc-2017.util :as util]
            [clojure.string :as str]))

(def input-str (util/read-tsv "d02.tsv"))
(def input (map (fn [row] (map #(Integer/parseInt %) row)) input-str))

(defn part-1
  "Given a collection `coll` of lists, sum the max difference for each list."
  [coll]
  (->> coll
       (map #(- (apply max %) (apply min %)))
       (reduce +)))

(defn- max-quotient
  "Return the greatest integer quotient from the input collection."
  [coll]
  (apply max
         (for [x coll
               y coll
               :when (and (not= x y)
                          (zero? (mod x y)))]
           (/ x y))))

(defn part-2
  "Given a collection `coll` of lists, sum the max integer quotients from each list."
  [coll]
  (reduce + (map max-quotient coll)))
