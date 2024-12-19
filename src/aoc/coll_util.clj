(ns aoc.coll-util
  (:require clojure.set))

(defn count-truthy [coll]
  "Returns the count of truthy items in `coll`"
  ;; does not create an intermediate collection like `filter true?`
  (reduce (fn [n val] (if val (inc n) n)) 0 coll))

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

(defn list-remove
  "Return the `xs` input list with the `i`th item removed."
  [xs i]
  (concat (take i xs) (drop (inc i) xs)))

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

(defn intervals
  "Returns the seq of intervals between each element of `xs`, step `n` (default 1)"
  ([xs] (intervals 1 xs))
  ([n xs] (map - (drop n xs) xs)))

(defn tree-seq-depth
  "Returns a lazy sequence of the nodes in a tree in a tuple with their
   depth, via a depth-first walk.  Directly copied from `tree-seq`.

   branch? must be a fn of one arg that returns true if passed a node
   that can have children (but may not).  children must be a fn of one
   arg that returns a sequence of the children. Will only be called on
   nodes for which branch? returns true. Root is the root node of the
   tree."
  [branch? children root]
  (let [walk (fn walk [depth node]
               (lazy-seq
                (cons [depth node]
                      (when (branch? node)
                        (mapcat (partial walk (inc depth)) (children node))))))]
    (walk 0 root)))

(defn x-nth
  "Returns the `n`th element of sequence `xs`.  Works like `clojure.core/nth` but
  reverses the order of the arguments so we can use in thread-last pipelines."
  [n xs]
  (nth xs n))

(defn heads
  "Returns a sequence of all prefixes of this sequence, longest first."
  [xs]
  (if-not (seq xs)
    '(())
    (cons xs (lazy-seq (heads (butlast xs))))))

(defn tails
  "Returns a sequence of all suffixes of this sequence, longest first."
  [xs]
  (if-not (seq xs)
    '(())
    (cons xs (lazy-seq (tails (rest xs))))))

(defn midpoint
  "Returns the value at the midpoint of the collection."
  [coll]
  {:pre [(odd? (count coll))]}
  (nth coll (quot (count coll) 2)))
