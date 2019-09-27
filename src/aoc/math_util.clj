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

