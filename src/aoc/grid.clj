(ns aoc.grid
  (:require [aoc.coll-util :as coll-util]
            [clojure.string :as str]))

(defn parse-move
  "Transform a move, represented by a concatenated string of dir and dist,
  into a tuple of dir and dist.
  e.g. 'R5' => [\\R 5]"
  [move]
  ((juxt first #(Integer/parseInt (str/join (rest %)))) (str/trim move)))

(def dir->delta {\U [ 0  1]
                 \D [ 0 -1]
                 \L [-1  0]
                 \R [ 1  0]

                 \N [ 0  1]
                 \E [ 1  0]
                 \W [-1  0]
                 \S [ 0 -1]})

(def origin [0 0])

(defn theta
  "Return the fractional gradient between two Cartesian points"
  [[x y]]
  (Math/atan2 y x))

(defn angle
  "Returns degrees from 0->360 of angle between origin and point
  using arctan2, normalized to 360"
  [point]
  (let [deg (Math/toDegrees (theta point))]
    (if (neg? deg)
      (+ 360 deg)
      deg)))

(defn vector-diff
  "Return the vector difference of the second arg subtracted from the first,
  given 2 Cartesian points.  e.g. (vector-diff [10 10] [1 2]) => [9 8]"
  [[x1 y1] [x2 y2]]
  [(- x1 x2) (- y1 y2)])

(defn vector-add
  "Return the vector sum of the args given 2 Cartesian points.
  e.g. (vector-sum [10 10] [1 2]) => [11 12]"
  [[x1 y1] [x2 y2]]
  [(+ x1 x2) (+ y1 y2)])

(defn distance [[x1 y1] [x2 y2]]
  (Math/sqrt (+ (Math/pow (- x2 x1) 2)
                (Math/pow (- y2 y1) 2))))

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

(defn move->delta
  [[dir mag]]
  (mapv #(* mag %) (dir->delta dir)))

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

(def neighbor-deltas [[-1 -1] [0 -1] [1 -1]
                      [-1  0] ,,,,,, [1  0]
                      [-1  1] [0  1] [1  1]])

(defn neighbor-coords
  "Return the 8 cartesian coord tuples surrounding input coord."
  [[x y]]
  (map (fn [[dx dy]] [(+ x dx) (+ y dy)]) neighbor-deltas))

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

(defn transpose-pad
  "Return a transposed matrix, where input matrix is made square
  by padding with `pad` at end of input rows as neccesary"
  [pad matrix]
  (->> matrix
       (map (partial coll-util/lazy-pad pad))
       (apply mapcat list)))

(defn point->idx
  "Returns array index for given `[x y]` coordinate."
  [[x y] x-width]
  (+ y (* x x-width)))

(defn wrap-coords
  "Return the [x y] coords in-bounds of a grid that wraps at `max-x` and `max-y`"
  [max-x max-y [x y]]
  [(rem x max-x) (rem y max-y)])
