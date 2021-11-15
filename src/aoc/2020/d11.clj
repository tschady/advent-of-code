(ns aoc.2020.d11
  (:require [aoc.coll-util :as coll-util]
            [aoc.file-util :as file-util]
            [aoc.grid :as grid]))

(def input (file-util/read-lines "2020/d11.txt"))

(def glyph->val {\. :floor
                 \# :occupied
                 \L :empty})

(defn step-grid
  "Advance grid 1 tick in time, according to the following rules:
  - A seat which is empty becomes occupied if it has 0 occupied neighbors.
  - A seat which is occupied becomes empty if more than `neighbor-count` neighbors are also occupied.
  - Others are unchanged.
  Neighbors of a cell are determined by applying `neighbor-fn`"
  [grid neighbor-fn neighbor-count]
  (reduce (fn [new-grid [loc val]]
            (let [neighbors-occupied (count (filter #(= :occupied %) (neighbor-fn grid loc)))
                  new-val (cond
                            (and (= :empty val) (= 0 neighbors-occupied)) :occupied
                            (and (= :occupied val) (<= neighbor-count neighbors-occupied)) :empty
                            :else val)]
              (assoc new-grid loc new-val)))
          {}
          grid))

(defn solve [input neighbor-fn neighbor-count]
  (->> (grid/build-grid input glyph->val)
       (iterate #(step-grid % neighbor-fn neighbor-count))
       (coll-util/first-duplicate)
       first
       vals
       (filter #(= :occupied %))
       count))

(defn los
  "Return the first glyph of `grid` in the line of sight from `loc` looking in
  the direction of `slope`"
  [grid loc slope]
  (let [inspect-loc (grid/vector-add loc slope)
        glyph (get grid inspect-loc)]
    (if (= :floor glyph)
      (recur grid inspect-loc slope)
      glyph)))

(defn neighbors-los
  [grid loc]
  (->> grid/neighbor-deltas
       (map (partial los grid loc))))

(defn part-1 [input] (solve input grid/neighbors 4))

(defn part-2 [input] (solve input neighbors-los 5))
