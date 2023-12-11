(ns aoc.2023.d11
  (:require
   [aoc.coll-util :refer [tails]]
   [aoc.file-util :as f]
   [aoc.grid :as grid]
   [aoc.math-util :refer [between?]]
   [aoc.matrix :as matrix]
   [medley.core :as medley]))

(def input (f/read-lines "2023/d11.txt"))

(defn expanders [mat]
  {:y (keep-indexed #(when (every? #{\.} %2) %1) mat)
   :x (keep-indexed #(when (every? #{\.} %2) %1) (matrix/transpose mat))})

(defn make-galaxy [input]
  (keys (medley/filter-vals #{\#} (grid/build-grid input identity))))

(defn galactic-dist
  [expanders size [x0 y0] [x1 y1]]
  (let [x-gaps (count (filter #(between? % x0 x1) (:x expanders)))
        y-gaps (count (filter #(between? % y0 y1) (:y expanders)))
        d (+ (abs (- x1 x0)) (abs (- y1 y0)))]
    (+ d (* (dec size) (+ x-gaps y-gaps)))))

(defn dists [gaps scale [start & ends]]
  (reduce + (map #(galactic-dist gaps scale start %) ends)))

(defn solve [input scale]
  (let [gaps (expanders input)]
    (->> (make-galaxy input)
         tails
         (map (partial dists gaps scale))
         (reduce +))))

(defn part-1 [input] (solve input 2))

(defn part-2 [input] (solve input 1000000))
