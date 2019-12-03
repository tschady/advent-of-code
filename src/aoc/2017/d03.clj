(ns aoc.2017.d03
  (:require [flatland.ordered.map :refer [ordered-map]]
            [aoc.grid :as grid]))

(def input 368078)

(defn- num->ring
  "Given a value `n` in the spiral, determine in which outward ring it occurs.
  The ring count is zero based."
  [n]
  (-> n Math/sqrt dec (* 1/2) Math/ceil int))

(defn- ring->max-val
  "Given a ring `n` in the spiral, compute the maximum value in that ring."
  [n]
  (-> n (* 2) inc (Math/pow 2) int))

(defn- ring->cardinal-vals
  "Given a ring `n`, return the 4 values at the cardinal directions."
  [n]
  (->> (range 4)
       (map #(* 2 n %))
       (map #(+ n (ring->max-val (dec n)) %))))

(defn- dist-to-cardinal
  "Find the distance of the target val `n` from the nearest cardinal value
  of that ring."
  [n cardinals]
  (->> cardinals
       (map #(- n %))
       (map #(Math/abs %))
       (apply min)))

(defn part-1
  "The Manhattan distance of the value `n` from the center of the spiral
  is the sum of which outer ring the value is on, plus the distance from
  the nearest cardinal direction.  O(1) solution."
  [n]
  (let [ring (num->ring n)
        cardinals (ring->cardinal-vals ring)]
    (+ ring (dist-to-cardinal n cardinals))))

;; Part 2 has completely separate code

(def spiral-steps
  "Infinite lazy sequence of directions of each step to form the spiral.
  Each side of the spiral gets longer each rotation."
  (let [step-len (mapcat #(repeat 2 %) (map inc (range)))
        step-dir (cycle [[1 0] [0 1] [-1 0] [0 -1]])]
    (mapcat repeat step-len step-dir)))

(defn- neighbor-sum
  "Given the coords `loc` of a node in `spiral`, compute the sum of it's
  nearest 8 neigbors."
  [loc spiral]
  (reduce + 0 (grid/neighbors spiral loc)))

(defn- add-next-node
  "Return a new spiral with next node added."
  [spiral step]
  (let [last-node (last spiral)
        new-loc (mapv + (first last-node) step)
        new-val (neighbor-sum new-loc spiral)]
    (assoc spiral new-loc new-val)))

(def origin (ordered-map [0 0] 1))

(def lazy-spiral (reductions add-next-node origin spiral-steps))

(defn part-2
  "Return the value of the first node with value greater than `n`."
  [n]
  (val (last (first (drop-while #(< (val (last %)) n) lazy-spiral)))))
