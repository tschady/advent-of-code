(ns aoc.2021.d09
  (:require
   [aoc.file-util :as f]
   [aoc.grid :as g]))

(def input (vec (f/read-lines "2021/d09.txt")))

(defn- low-point? [grid [loc height]]
  (let [neighbors (g/neighbors grid loc g/neighbor-coords-news)]
    (every? (partial < height) neighbors)))

(defn basin? [grid _ loc]
  (let [v (get grid loc)]
    (not (or (nil? v) (= 9 v)))))

(defn part-1 [input]
  (let [grid (g/build-grid input #(Character/getNumericValue %))]
    (->> (filter (partial low-point? grid) grid)
         (map (comp inc second))
         (reduce +))))

;; ^:blog
;; A straightforward problem.  Notably, I was able to reuse my `grid` library
;; to build the grid, find neighbors, and create the graph in the form of an adjacency map.

(defn ^:blog part-2 [input]
  (let [grid (g/build-grid input #(Character/getNumericValue %))]
    (->> (filter (partial low-point? grid) grid)
         (map first)
         (map (partial g/connected-adjacency-map (partial basin? grid) g/neighbor-coords-news))
         (map count)
         (sort >)
         (take 3)
         (reduce *))))
