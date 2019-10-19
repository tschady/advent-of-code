(ns aoc.math-util)

(defn manhattan-dist
  "Return the manhattan distance between two points, where each point is a
  vector of cartesian coordinates.  Works for n-dimensions."
  [a b]
  (reduce + (map (comp #(Math/abs ^long %) -) a b)))

(defn first-duplicate
  "For given coll, return a vector of first duplicate entry, and the two
  indexes of each occurence.
  e.g. [<target entry> [<first index> <matching index>]"
  [coll]
  (reduce (fn [acc [idx x]]
            (if-let [v (get acc x)]
              (reduced [x (conj v idx)])
              (assoc acc x [idx])))
          {} (map-indexed vector coll)))

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
