(ns aoc.2021.d09
  (:require
   [aoc.file-util :as f]
   [aoc.grid :as g]))

(def input (vec (f/read-lines "2021/d09.txt")))

(defn- low-point? [grid [loc height]]
  (let [neighbors (g/neighbors grid loc g/neighbor-coords-news)]
    (every? (partial < height) neighbors)))

(defn basin? [grid loc]
  (let [v (get grid loc)]
    (not (or (nil? v) (= 9 v)))))

(defn part-1 [input]
  (let [grid (g/build-grid input #(Character/getNumericValue %))]
    (->> (filter (partial low-point? grid) grid)
         (map (comp inc second))
         (reduce +))))

(defn part-2 [input]
  (let [grid (g/build-grid input #(Character/getNumericValue %))]
    (->> (filter (partial low-point? grid) grid)
         (map first)
         (map (partial g/connected-adjacency-map (partial basin? grid) g/neighbor-coords-news))
         (map count)
         (sort >)
         (take 3)
         (reduce *))))
