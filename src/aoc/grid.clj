(ns aoc.grid
  (:require [clojure.string :as str]))

(defn parse-move
  "Transform a move, represented by a concatenated string of dir and dist,
  into a tuple of dir and dist.
  e.g. 'R5' => [\\R 5]"
  [move]
  ((juxt first #(Integer/parseInt (str/join (rest %)))) (str/trim move)))

(def dir->delta {\U [0 1]
                 \D [0 -1]
                 \L [-1 0]
                 \R [1 0]})

(def origin [0 0])

(defn step-turns
  "Determine series of cartesian deltas from list of moves, where each
  move is given by it's relative turn ('R' or 'L'), and magnitude."
  [moves]
  (let [bearings [[0 -1] [1 0] [0 1] [-1 0]]
        dir->adjust-bearing {\R inc, \L dec}
        turns (map first moves)
        dists (map second moves)
        indexes (->> turns
                     (reductions #(mod ((dir->adjust-bearing %2) %1) 4) 0)
                     rest)
        deltas (map bearings indexes)]
    (mapcat repeat dists deltas)))

(defn step-moves
  "Determine series of cartesian deltas from list of moves, where each
  move is given by it's direction (U,D,L,R), and magnitude."
  [moves]
  (mapcat (fn [[dir magnitude]] (repeat magnitude (dir->delta dir))) moves))

(defn walk
  "Return every location reached by applying steps given by deltas."
  [from deltas]
  (reductions (partial mapv +) from deltas))

(defn turns->path
  [step-instructions]
  (->> step-instructions (map parse-move) step-turns (walk origin)))

(defn moves->path
  [move-instructions]
  (->> move-instructions (map parse-move) step-moves (walk origin)))

(defn manhattan-dist
  "Return the manhattan distance between two points, where each point is a
  vector of cartesian coordinates.  Works for n-dimensions."
  [a b]
  (reduce + (map (comp #(Math/abs ^long %) -) a b)))

(defn neighbor-coords
  "Return the 8 cartesian coord tuples surrounding input coord."
  [[x y]]
  (for [dx [-1 0 1]
        dy [-1 0 1]
        :when (not= 0 dx dy)]
    [(+ x dx) (+ y dy)]))

(defn neighbors
  "Return the contents of the 8 surrounding neighbors in the grid.
  Off grid cells are not returned."
  [grid loc]
  (->> loc
       neighbor-coords
       (map grid)
       (remove nil?)))

(defn build-grid
  "Return map of coordinates to a value, given list of strings of glyphs,
  and a mapping of glyph->val."
  [lines glyph->val]
  (into {} (for [y (range (count lines))
                 x (range (count (nth lines y)))]
             {[x y] (glyph->val (get-in lines [y x]))})))
