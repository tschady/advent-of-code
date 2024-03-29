(ns aoc.2021.d11
  (:require
   [aoc.file-util :as file-util]
   [aoc.grid :refer [neighbor-coords build-grid]]
   [com.rpl.specter :refer [MAP-VALS transform]]))

(def input (file-util/read-lines "2021/d11.txt"))

(defn flash [grid [loc v]]
  {:pre [(> v 9)]}
  (reduce (fn [grid neighbor]
            (if (zero? (get grid neighbor 0))
              grid ; already flashed this one, don't increment
              (update grid neighbor inc)))
          (assoc grid loc 0) ; flash it
          (neighbor-coords loc)))

;; ^:blog
;; The core `step` function used in `iterate`.  By iterating, we do not need to
;; track any intermediate state like the zero count since we can sum over all the
;; states reached.
;;
;; [NOTE]
;; ====
;; My approach to these problems is to start from the outside in.  In this case,
;; I typed `(reduce flash grid flashers)` before anything else.
;; ====

(defn ^:blog step [grid]
  (loop [grid (transform [MAP-VALS] inc grid)]
    (if-let [flashers (seq (filter #(> (val %) 9) grid))]
      (recur (reduce flash grid flashers))
      grid)))

(defn part-1 [input]
  (->> (build-grid input #(Character/digit % 10))
       (iterate step)
       (take 101)
       (mapcat vals)
       (filter zero?)
       count))

(defn part-2 [input]
  (->> (build-grid input #(Character/digit % 10))
       (iterate step)
       (take-while #(not (every? zero? (vals %))))
       count))
