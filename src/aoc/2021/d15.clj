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

(defn ^:blog part-1 [input]
  (let [size (count input)]
    (-> input
        (grid/build-grid #(Character/digit % 10))
        (safest-path [0 0] [(dec size) (dec size)])
        :cost)))

;; ^:blog Part 2 is solved the same way after expanding the grid.
;; The input data helped here, as I originally just did `mod` and had 0s
;; in my output.

(defn ^:blog expand-grid [grid size]
  (apply merge (for [loc  (keys grid)
                     dx   (range 5)
                     dy   (range 5)
                     :let [[x y] loc
                           risk (get grid loc)
                           new-risk (inc (mod (+ dx dy risk -1) 9))]]
                 {[(+ x (* size dx)) (+ y (* size dy))] new-risk})))

(defn part-2 [input]
  (let [size (count input)]
    (-> input
        (grid/build-grid #(Character/digit % 10))
        (expand-grid size)
        (safest-path [0 0] [(dec (* 5 size)) (dec (* 5 size))])
        :cost)))
