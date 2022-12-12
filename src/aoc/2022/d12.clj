(ns aoc.2022.d12
  (:require
   [aoc.file-util :as f]
   [aoc.grid :as grid]
   [aoc.string-util :as s]
   [ubergraph.alg :as alg]
   [ubergraph.core :as uber]))

(def input (f/read-lines "2022/d12.txt"))

(def heights (merge (zipmap s/alphabet-lower (range))
                    {\S 0, \E 25}))

(defn parse [input]
  (let [gc (grid/build-grid input char)]
    [(update-vals gc heights)
     (first (grid/locate gc \S))
     (first (grid/locate gc \E))]))

(defn walkable? [grid loc neighbor]
  (let [h0 (get grid loc)
        h1 (get grid neighbor ##Inf)]
    (>= 1 (- h1 h0))))

(defn build-graph [grid]
  (uber/digraph (grid/connected-adjacency-map
                 (partial walkable? grid)
                 grid/neighbor-coords-news
                 origin)))

(defn part-1 [input]
  (let [[grid start end] (parse input)]
    (:cost (alg/shortest-path (build-graph grid) start end))))

(defn part-2 [input]
  (let [[grid _ end] (parse input)
        starts (grid/locate grid 0)]
    (:cost (alg/shortest-path (build-graph grid) {:start-nodes starts
                                                  :end-node    end}))))
