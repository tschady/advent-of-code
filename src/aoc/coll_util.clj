(ns aoc.coll-util
  (:require clojure.set))

(defn idx-of-max
  "Return the index of the maximum value of the collection."
  [coll]
  (->> coll
       (map-indexed vector)
       (apply max-key second)
       first))

(defn submap?
  "Returns true if `m1` is a proper subset of `m2`, else false."
  [m1 m2]
  (clojure.set/subset? (set m1) (set m2)))

(defn lazy-pad
  "Return a lazy sequence which pads sequence `xs` with `pad` value."
  [pad xs]
  (if (empty? xs)
    (repeat pad)
    (lazy-seq (cons (first xs) (lazy-pad pad (rest xs))))))

(defn vec-remove
  "Return the `coll` input vector with the `i`th item removed."
  [coll i]
  (vec (concat (subvec coll 0 i) (subvec coll (inc i)))))

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
