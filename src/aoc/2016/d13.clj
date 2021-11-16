(ns aoc.2016.d13
  (:require
   [aoc.file-util :as file-util]
   [aoc.grid :as grid]
   [aoc.math-util :as math-util]
   [ubergraph.alg :as alg]
   [ubergraph.core :as uber]))

(def input (first (file-util/read-ints "2016/d13.txt")))

(defn *open-space?
  "Return true if given coord is an open space within problem bounds given by `size`, else false."
  [input size [x y]]
  (and (<= 0 x size)
       (<= 0 y size)
       (-> (+ (* x x) (* 3 x) (* 2 x y) (* y y) y input)
           (math-util/count-on-bits)
           even?)))

(def open-space? (memoize *open-space?))

(defn part-1 [input start-loc end-loc size]
  (-> (grid/connected-adjacency-map (partial open-space? input size)
                                    grid/neighbor-coords-news
                                    [0 0])
      uber/graph
      (alg/shortest-path start-loc end-loc)
      :cost))

(defn part-2 [input start-loc max-dist]
  ;; There's a bug in my understanding of the problem description...
  ;; I would say it's 0 hops from start-pos to start-pos, but only get the right answer
  ;; if I consider this cost 1.  Thus the (dec max-dist) below.
  (-> (grid/connected-adjacency-map (partial open-space? input max-dist)
                                    grid/neighbor-coords-news
                                    [0 0])
      uber/graph
      (alg/shortest-path {:start-node start-loc :traverse true :max-cost (dec max-dist)})
      count))
