(ns aoc.2015.d18
  (:require [aoc.math-util :refer [build-grid neighbors]]
            [aoc.file-util :as file-util]))

(def input (file-util/read-lines "2015/d18.txt"))

(def glyph->val {\. 0
                 \# 1})

(defn step-grid
  "Advance grid 1 tick in time, according to the following rules:
  - A light which is on stays on when 2 or 3 neighbors are on, and turns off otherwise.
  - A light which is off turns on if exactly 3 neighbors are on, and stays off otherwise."
  [grid]
  (reduce (fn [new-grid [loc val]]
            (let [neighbors-on (reduce + 0 (neighbors grid loc))
                  new-val (cond
                            (and (pos? val) (< 1 neighbors-on 4)) 1
                            (and (zero? val) (= 3 neighbors-on)) 1
                            :else 0)]
              (assoc new-grid loc new-val)))
          {}
          grid))

(defn solve [input step-fn]
  (->> (build-grid input glyph->val)
       (iterate step-fn)
       (take (inc 100))
       last
       (map second)
       (reduce + 0)))

(def white-pixels '([ 0 0] [ 0 99]
                    [99 0] [99 99]))

(defn- force-lights [grid]
  (reduce #(assoc % %2 1) grid white-pixels))

(defn part-1 [input] (solve input step-grid))

(defn part-2 [input] (solve input (comp force-lights step-grid)))
