(ns aoc.2021.d15
  (:require
   [aoc.file-util :as file-util]
   [aoc.grid :as grid]
   [ubergraph.alg :as alg]
   [ubergraph.core :as uber]))

(def input (file-util/read-lines "2021/d15.txt"))

;; ^:blog
;; First graph problem of the year.
;;
;; [IMPORTANT]
;; ====
;; (Is your https://www.reddit.com/r/adventofcode/comments/k3q7tr/my_advent_of_code_2020_bingo_card_fun_little_side/[AdventOfCode bingo card] complete yet?)
;; ====
;;
;; You *could* type out Djikstra's algorithm, using Java's PriorityQueue
;; or `clojure.data.priority-map`.  Or, you could just slam it all into
;; a graph library and Keep 'er Movin'.
;;
;; My go-to for these is https://github.com/Engelberg/ubergraph[ubergraph].
;; There was some ambiguity in the constructor for edges
;; (since the nodes were also `[x y]` vectors),
;; so I had to build an empty graph and use the explicit `add-edges*`

(defn ^:blog edges [risks]
  (for [loc      (keys risks)
        neighbor (grid/neighbor-coords-news loc)
        :let     [risk (get risks neighbor)]
        :when    (some? risk)]
    [loc neighbor {:weight risk}]))

(defn ^:blog safest-path [risk-grid start end]
  (-> (uber/multidigraph)
      (uber/add-edges* (edges risk-grid))
      (alg/shortest-path start end :weight)))

;; ^:blog Part 2 is solved the same way after expanding the grid.
;; The example data helped here, as I originally just did `mod` and had 0s
;; in my output.  This "modify, subtract 1, mod 9, increment 1" probably
;; has a simpler expression.

(defn ^:blog expand-grid [grid magnifier]
  (let [[width height] (grid/size grid)]
    (apply merge (for [loc (keys grid)
                       dx   (range magnifier)
                       dy   (range magnifier)
                       :let [[x y] loc
                             risk (get grid loc)
                             new-risk (inc (mod (+ dx dy risk -1) 9))]]
                   {[(+ x (* width dx)) (+ y (* height dy))] new-risk}))))

;; ^:blog
;; Expanding the grid with size 1 for part-1 in an expensive no-op,
;; but I'm a sucker for generalizing the two parts.

(defn ^:blog solve [input magnifier]
  (let [g (-> input
              (grid/build-grid #(Character/digit % 10))
              (expand-grid magnifier))
        end (mapv dec (grid/size g))]
    (:cost (safest-path g [0 0] end))))

(defn ^:blog part-1 [input] (solve input 1))

(defn ^:blog part-2 [input] (solve input 5))
