(ns aoc.hex
  (:require [aoc.math-util :as math-util]))

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

(defn distance
  ([a] (distance a [0 0 0]))
  ([a b] (/ (reduce + (map (comp #(Math/abs %) -) a b)) 2)))

(defn walk
  "Return the hex delta (in [q r s] coords) of applying cardinal direction `steps`"
  [orientation steps]
  (->> steps
       (map (partial delta orientation))
       (math-util/vector-math +)))

(defn step
  "Return location resulting from walking 1 step from `loc` in direction `dir`.
  Useful as a reduction function, i.e. (partial step :flat-top)"
  [orientation loc dir]
  (mapv + loc (delta orientation dir)))
