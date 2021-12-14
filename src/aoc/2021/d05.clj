(ns aoc.2021.d05
  (:require [aoc.file-util :as file-util]))

(def input (map (partial partition 2) (file-util/read-int-vectors "2021/d05.txt")))

;; ^:blog
;; I use slope and y-intercept to compute points on the line. The interesting part is
;; use of Infinity: `##Inf` which works well mathematically, but does require the occasional 'if'.

(defn- ^:blog slope [[[x1 y1] [x2 y2]]]
  (if (= x2 x1)
    ##Inf
    (/ (- y2 y1) (- x2 x1))))

(defn y-intercept [[[x1 y1] [_x2 _y2] :as line]]
  (- y1 (* x1 (slope line))))

(defn line->points [line]
  (let [[[x1 y1] [x2 y2]] (sort-by (juxt first second) line)]
    (if (= ##Inf (slope line))
      (map vector (repeat x1) (range y1 (inc y2)))
      (for [x (range x1 (inc x2))
            :let [y (+ (* x (slope line)) (y-intercept line))]]
        [x y]))))

(defn solve [slopes input]
  (->> input
       (filter #(contains? slopes (slope %)))
       (mapcat line->points)
       frequencies
       (remove #(= 1 (val %)))
       count))

(defn part-1 [input] (solve #{0 ##Inf} input))

;; ^:blog
;; This lets me describe horizontal/vertical/diagonal lines by their slope.

(defn ^:blog part-2 [input] (solve #{0 ##Inf 1 -1} input))
