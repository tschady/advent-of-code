(ns aoc.2021.d10
  (:require [aoc.file-util :as file-util]
            [aoc.math-util :as math-util]))

(def input (file-util/read-lines "2021/d10.txt"))

(def mates {\] \[, \) \(, \} \{, \> \<})

(defn- opener? [c] (contains? (set (vals mates)) c))

(def corrupt-scores {\) 3, \] 57, \} 1197, \> 25137})

(def incomplete-scores {\( 1, \[ 2, \{ 3, \< 4})

(defn stackify
  "Returns either:
    - the stack of unclosed openers as a vector (empty in the case of balance)
    - the first offending unmatched closing char.
  Throws Assertion error if `s` contains any invalid chars."
  [s]
  {:pre [(every? (set (concat (keys mates) (vals mates))) s)]}
  (reduce (fn [stack c]
            (cond
              (opener? c)                (conj stack c)
              (= (mates c) (peek stack)) (pop stack)
              :else                      (reduced c)))
          []
          s))

(defn part-1 [input]
  (->> input
       (map stackify)
       (remove coll?)
       (map corrupt-scores)
       (reduce +)))

(defn score-incomplete [xs]
  (reduce (fn [score sym] (+ (incomplete-scores sym) (* 5 score)))
          0
          (reverse xs)))

(defn part-2 [input]
  (->> input
       (map stackify)
       (filter coll?)
       (map score-incomplete)
       math-util/median))
