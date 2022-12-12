(ns aoc.grid
  (:refer-clojure :exclude [print])
  (:require
   [aoc.coll-util :as coll-util]
   [aoc.string-util :as s]
   [clojure.core.matrix :as mat]
   [clojure.core.matrix.linear :as linear]
   [medley.core :as medley]))

(defn parse-move
  "Transform a move, represented by a concatenated string of dir and dist,
  into a tuple of dir and dist.
  e.g. 'R5' => [\\R 5], 'R 5' => [\\R 5]"
  [move]
  ((juxt first #(first (s/ints %))) move))

(def dir->delta {\U [ 0  1]
                 \D [ 0 -1]
                 \L [-1  0]
                 \R [ 1  0]

                 \N [ 0  1]
                 \E [ 1  0]
                 \W [-1  0]
                 \S [ 0 -1]})

(defn flat->coord
  "Return the [x y] cartesian coordinates of index `i` in a flat array."
  [i size]
  [(rem i size) (quot i size)])

(defn coord->flat
  "Return the flat array index of a cartesian coordinate [x y]."
  [[x y] size]
  (+ x (* y size)))

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
  (-> (mat/sub a b)
      (linear/norm 1)
      int))

(def neighbor-deltas [[-1 -1] [0 -1] [1 -1]
                      [-1  0] ,,,,,, [1  0]
                      [-1  1] [0  1] [1  1]])

(defn area-deltas
  "Returns the delta to locations up to `n` spaces out from center,
  unlike other `neighbor-*` functions, this includes the center square."
  [n]
  (for [y (range (* -1 n) (inc n))
        x (range (* -1 n) (inc n))]
    [x y]))

(def neighbor-deltas-3d
  (for [x [-1 0 1]
        y [-1 0 1]
        z [-1 0 1]
        :when (not= 0 x y z)]
    [x y z]))

(def neighbor-deltas-4d
  (for [x [-1 0 1]
        y [-1 0 1]
        z [-1 0 1]
        w [-1 0 1]
        :when (not= 0 x y z w)]
    [x y z w]))

(defn neighbor-coords
  "Return the cartesian coord tuples surrounding input coord in 2-d space,
  with neighbors returned by `neighbors`, defaulting to the 8 surrounding cells."
  ([[x y]] (neighbor-coords [x y] neighbor-deltas))
  ([[x y] neighbors]
   (map (fn [[dx dy]] [(+ x dx) (+ y dy)]) neighbors)))

(defn neighbor-coords-news
  "Return the 4 cartesian coord tuples in the surrounding cardinal directions."
  [[x y]]
  [[x (inc y)] [(inc x) y] [(dec x) y] [x (dec y)]])

(defn neighbor-coords-3d
  "Return the 26 cartesian coord tuples surrounding input coord in 3-d space."
  [[x y z]]
  (map (fn [[dx dy dz]] [(+ x dx) (+ y dy) (+ z dz)]) neighbor-deltas-3d))

(defn neighbor-coords-4d
  "Return the 8 cartesian coord tuples surrounding input coord in 4-d space."
  [[x y z w]]
  (map (fn [[dx dy dz dw]] [(+ x dx) (+ y dy) (+ z dz) (+ w dw)]) neighbor-deltas-4d))

(defn neighbors
  "Return the contents of the 8 surrounding neighbors in the grid.
  Off grid cells are not returned."
  ([grid loc] (neighbors grid loc neighbor-coords))
  ([grid loc neighbor-fn]
   (->> loc
        neighbor-fn
        (map grid)
        (remove nil?))))

(defn build-grid
  "Return map of coordinates to a value, given list of strings of glyphs,
  and a mapping of glyph->val."
  [lines glyph->val]
  (into {} (for [y (range (count lines))
                 x (range (count (nth lines y)))]
             {[x y] (glyph->val (get-in lines [y x]))})))

(defn size
  "Returns the a tuple of max dimensions along [x y]"
  [grid]
  (let [max-x (apply max (map first (keys grid)))
        max-y (apply max (map second (keys grid)))]
    [(inc max-x) (inc max-y)]))

(defn grid-min-max [grid]
  [(reduce min (map first (keys grid)))
   (reduce max (map first (keys grid)))
   (reduce min (map second (keys grid)))
   (reduce max (map second (keys grid)))])

(defn print-grid-to-array
  "Return ASCII representation of grid, given hashmap of coords to glyphs."
  ([grid] (print-grid-to-array \space grid))
  ([off grid]
   (let [[min-x max-x min-y max-y] (grid-min-max grid)
         x-dim                     (- max-x min-x)]
     (->> (for [y (range min-y (inc max-y))
                x (range min-x (inc max-x))]
            (get grid [x y] off))
          (partition (inc x-dim))
          (map (partial apply str))))))

(def print print-grid-to-array)

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

(defn connected-adjacency-map
  "Returns a node adjacency map where nodes are `grid` coords, and edges
  exist if both nodes are 'open', where openness is determined by
  `open-fn?`, which is a function which takes two arguments: the
  current location and the potential next location.  Map entries are
  in form {[0 0] '([0 1] [1 0])}, suitable for graph library
  constructors.  Note: only nodes connected via `neighbor-fn` from the
  `origin` node are present."
  [open-fn? neighbor-fn origin]
  (loop [nodes   (list origin)
         seen    #{}
         adj-map {}]
    (if (empty? nodes)
      adj-map
      (let [loc            (first nodes)
            open-neighbors (filter (partial open-fn? loc) (neighbor-fn loc))]
        (recur (into (rest nodes) (remove seen open-neighbors))
               (conj seen loc)
               (into adj-map (hash-map loc open-neighbors)))))))

(defn locate
  "Returns the collection of coords in grid `g` that contain value `v`"
  [g v]
  (keys (medley/filter-vals #{v} g)))
