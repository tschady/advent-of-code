(ns aoc.2020.d03
  (:require [aoc.file-util :as file-util]
            [aoc.grid :as grid]))

(def input (file-util/read-lines "2020/d03.txt"))

(defn tree-count
  "Return number of trees encountered in `input` grid when traveling with slope `[dx dy]`.
  Grid wraps infinitely in the x direction."
  [input [dx dy]]
  (let [grid (grid/build-grid input {\# 1, \. 0})
        grid-x (count (first input))
        grid-y (count input)]
    (->> (iterate (partial mapv + [dx dy]) [0 0]) ; get all hops
         (map (partial grid/wrap-coords grid-x Integer/MAX_VALUE))  ; wrap to fit grid
         (take (quot grid-y dy))        ; stop at bottom of grid
         (map grid)                     ; find trees
         (reduce +))))

(defn part-1 [input] (tree-count input [3 1]))

(defn part-2 [input]
  (->> '([1 1] [3 1] [5 1] [7 1] [1 2])
       (map (partial tree-count input))
       (reduce *)))
