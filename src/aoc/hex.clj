(ns aoc.hex
  (:require [aoc.grid :as grid]
            [aoc.math-util :as math-util]))

;; [resource on hex-coord systems and functions](https://www.redblobgames.com/grids/hexagons/)

(def dir->delta
  {:pointy-top {"w"  [-1 0 1]
                "e"  [1 0 -1]
                "nw" [0 -1 1]
                "ne" [1 -1 0]
                "sw" [-1 1 0]
                "se" [0 1 -1]}
   :flat-top   {"n"  [0 -1 1]
                "s"  [0 1 -1]
                "nw" [-1 0 1]
                "ne" [1 -1 0]
                "sw" [-1 1 0]
                "se" [1 0 -1]}})

(defn delta [orientation dir]
  (get-in dir->delta [orientation dir]))

(defn neighbor-deltas [orientation]
  (vals (get dir->delta orientation)))

(defn neighbors [orientation loc]
  (map #(mapv + loc %) (neighbor-deltas orientation)))
