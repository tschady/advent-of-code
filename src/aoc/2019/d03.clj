(ns aoc.2019.d03
  (:require [aoc.file-util :as file-util]
            [aoc.grid :as grid]
            [clojure.set :as set]))

(def input (file-util/read-csv "2019/d03.txt"))

(defn part-1
  [wires]
  (let [paths (map grid/moves->path wires)
        intersections (apply set/intersection (map set paths))
        non-origin-intersects (disj intersections grid/origin)
        distances (map #(grid/manhattan-dist grid/origin %) non-origin-intersects)]
    (apply min distances)))

(defn part-2
  [[w1 w2]]
  (let [p1 (grid/moves->path w1)
        p2 (grid/moves->path w2)
        intersections (set/intersection (set p1) (set p2))
        non-origin-intersects (disj intersections grid/origin)]
    (->> non-origin-intersects
         (map #(+ (.indexOf p1 %) (.indexOf p2 %)))
         (apply min))))
