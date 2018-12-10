(ns aoc.2018.d10
  (:require [aoc.file-util :as file-util]))

(def input (file-util/read-lines "2018/d10.txt"))

;; position=<-54217, -21587> velocity=< 5,  2>
(def pixel-pattern #"position=<\s*(-?\d+),\s+(-?\d+)> velocity=<\s*(-?\d+),\s+(-?\d+)>")

(defn make-pixel
  "Create pixel object from input string.  Pixels have Cartesian position and velocity."
  [s]
  (let [[_ x y dx dy] (map read-string (re-find pixel-pattern s))]
    {:x x :y y :dx dx :dy dy}))

(defn tick-pixel
  ""
  [{:keys [x y dx dy]}]
  {:x (+ x dx) :y (+ y dy) :dx dx :dy dy})

;;;;;
(def s1 "position=<2, 3> velocity=<1, 1>")
(def s2 "position=<-3, -2> velocity=<-1, 1>")
(def c [s1 s2])
(def p1 (make-pixel s1))
(def p2 (make-pixel s2))

(def ps (map make-pixel c))


(tick-pixel p2)
(map tick-pixel ps)
