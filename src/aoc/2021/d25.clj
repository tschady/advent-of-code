(ns aoc.2021.d25
  (:require
   [aoc.file-util :as file-util]
   [aoc.grid :as grid]
   [medley.core :refer [filter-vals remove-vals]]))

(def input (file-util/read-lines "2021/d25.txt"))

(def ->delta {\> [1 0], \v [0 1]})

(defn parse [input]
  (let [dy   (count input)
        dx   (count (first input))
        grid (remove-vals #{\.} (grid/build-grid input identity))]
    [dx dy grid]))

;; ^:blog
;; Straightforward reuse of `wrap-coords` to find any blockers.
;; I intentionally used `dissoc` and `assoc` because I thought
;; I'd switch to the transient versions later in a loop/recur
;; for speed.

(defn ^:blog shift [dx dy grid g [loc c]]
  (let [new-loc (->> loc
                     (grid/vector-add (->delta c))
                     (grid/wrap-coords dx dy))]
    (if (get grid new-loc)
      g
      (-> g
          (dissoc loc)
          (assoc new-loc c)))))

;; ^:blog
;; This is my first use of `as->`. I always thought it obfuscating,
;; but I like it here.

(defn ^:blog step [dx dy grid]
  (as-> grid g
    (reduce (partial shift dx dy g) g (filter-vals #{\>} grid))
    (reduce (partial shift dx dy g) g (filter-vals #{\v} grid))))

;; ^:blog
;; This is really the year of iterate.  I hope I return to these with
;; visualizations.

(defn ^:blog part-1 [input]
  (let [[dx dy grid] (parse input)]
    (->> grid
         (iterate (partial step dx dy))
         (partition 2 1)
         (take-while (partial apply not=))
         count
         inc)))

#_(time (part-1 input));
