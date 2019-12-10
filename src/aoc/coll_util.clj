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

