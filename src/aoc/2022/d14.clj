(ns aoc.2022.d14
  (:require
   [aoc.file-util :as f]
   [aoc.grid :as grid]
   [aoc.string-util :as s]
   [clojure.core.matrix :as m]
   [clojure.string :as str]
   [clojure2d.core :as c2d :refer :all]
   [fastmath.core :as math]))

(math/use-primitive-operators)

(def input (f/read-file "2022/d14.txt"))

(def source [500 0])

(defn segment->points [segment]
  (reduce (fn [locs point]
            (reduce conj locs (grid/line-coords (peek locs) point)))
          [(vec (first segment))]
          (rest segment)))

(defn parse-cave [s]
  (let [points (->> (str/split-lines s)
                    (map s/ints)
                    (map #(partition 2 %))
                    (mapcat segment->points))]
    (merge {source \+} (zipmap points (repeat \#)))))

(defn add-floor
  "Add an 'infinite' floor to the cave, which is 2 lower than the lowest
  point.  The floor width only needs to be large enough to handle a
  full triangle from the sand source."
  [cave]
  (let [[_ _ _ y] (grid/bounds cave)
        h (+ 2 y)
        w (inc h)
        floor (segment->points [[(- (first source) w) h]
                                [(+ (first source) w) h]])]
    (merge cave (zipmap floor (repeat \#)))))

;; This is very inefficient.  Optimizations to try:
;; - 'fork' the grain into the 3 possible states
;; - transient sand.  matrix library supports it.
;; - fastmath vectors instead of matrix lib
;; - backtrace: the next grain falls to at least one step back from the prev
;;   this is kind of like the cave filling in from the bottom with water
(defn grains-to-fill
  "Returns the number of grains of sand needed before the spout gets blocked
  or we have reached an infinite pouring state."
  [cave]
  (let [box (grid/bounds cave)]
    (loop [n    0
           cave cave
           sand source]
      (cond
        (grid/ob? box sand)                   n
        (nil? (get cave (m/add sand [0 1])))  (recur n cave (m/add sand [0 1]))
        (nil? (get cave (m/add sand [-1 1]))) (recur n cave (m/add sand [-1 1]))
        (nil? (get cave (m/add sand [1 1])))  (recur n cave (m/add sand [1 1]))
        (= sand source)                       n
        :else
        (recur (inc n) (conj cave {sand \o}) source)))))

(defn part-1 [input] (-> input parse-cave grains-to-fill))

(defn part-2 [input] (-> input parse-cave add-floor grains-to-fill inc))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Simulation for visualization, every time tick, extremely slow
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(defn simulate-add-sand [cave]
  (let [box (grid/bounds cave)]
    (loop [sand source]
      (cond
        (grid/ob? box sand)                   :infinite
        (nil? (get cave (m/add sand [0 1])))  (recur (m/add sand [0 1]))
        (nil? (get cave (m/add sand [-1 1]))) (recur (m/add sand [-1 1]))
        (nil? (get cave (m/add sand [1 1])))  (recur (m/add sand [1 1]))
        (= sand source)                       :blocked
        :else                                 (conj cave {sand \o})))))

(defn sim-p1-frames [input]
  (->> (parse-cave input)
       (iterate simulate-add-sand)
       (take-while #(not= :infinite %))))

(defn sim-p2-frames [input]
  (->> (parse-cave input)
       (add-floor)
       (iterate simulate-add-sand)
       (take-while #(not= :blocked %))))
