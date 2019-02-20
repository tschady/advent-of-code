(ns aoc.2015.d09
  (:require [aoc.file-util :as file-util]
            [clojure.math.combinatorics :as combo]
            [clojure.set :as set]))

(def input (file-util/read-lines "2015/d09.txt"))

(defn- parse-line
  "Transform text input of distance between two cities to a tuple with
  key as the set of two locations and value as the distance between."
  [s]
  (let [[_ a b dist] (re-find #"(\w+) to (\w+) = (\d+)" s)]
    [#{a b} (Integer/parseInt dist)]))

(defn solve
  "Return the result of applying function f to all possible distances
  traveled between cities in given collection."
  [coll f]
  (let [graph (into {} (map parse-line coll))
        cities (apply set/union (keys graph))
        possible-paths (combo/permutations cities)
        legs (fn [path] (map set (partition 2 1 path)))
        dist (fn [legs] (reduce + 0 (map graph legs)))]
    (apply f (->> possible-paths
                  (map legs)
                  (map dist)))))

(defn part-1
  "Find the minimum travel distance that visits each city in input string."
  [coll]
  (solve coll min))

(defn part-2
  "Find the max travel distance that visits each city once in input string."
  [coll]
  (solve coll max))
