(ns aoc.2021.d17
  (:require
   [aoc.file-util :as file-util]
   [aoc.math-util :as math-util :refer [quadratic series-sum]]))

(def input (file-util/read-ints "2021/d17.txt")); [29 73 -248 -194]

;; ^:blog
;; Much more fun problem!  More thinking, less typing.
;; For part 1, we know the probe will return to exactly `y=0` at
;; some `t` because of symmetry, so the max velocity will be
;; whatever barely fits in the box at the next step.

(defn ^:blog part-1 [[_ _ y0 _]]
  (series-sum (dec (Math/abs y0))))

(defn t-max-y [p]
  (let [max-v (dec (Math/abs p))]
    (int (first (quadratic 1 (* -1 (inc (* 2 max-v))) (* 2 p))))))

;; ^:blog Because dx/dt^2 is a step function (-1, then suddenly 0)
;; we simplify by capping t to when motion stops.  Y is typical.
;; TODO: equations

(defn ^:blog vx
  "Return the initial x-axis velocity to reach point `x` at time `t`.
  Because the X velocity stops at 0 forever, we determine that time
  with quadratic formula and cap results there."
  [x t]
  (let [t_vx0 (int (Math/round (first (quadratic 1 1 (* -2 x)))))
        t (min t t_vx0)]
    (/ (+ (* 2. x) (* t t) (* -1 t)) (* 2 t))))

(defn ^:blog vy
  "Return the initial y-axis velocity to reach point `y` at time `t`."
  [y t]
  (/ (+ t -1 (/ (* 2. y) t)) 2))

(defn ^:blog vel-range
  "Returns the range [endpoints) of velocities that fit in target
  box noted by `p_0 p_1` using velocity function `f` at time `t`."
  [f [p_0 p_1] t]
  [(int (Math/ceil (f p_0 t)))
   (inc (int (Math/floor (f p_1 t))))])

(defn ^:blog part-2 [[x_0 x_1 y_0 y_1]]
  (count (set (for [t (range 1 (inc (t-max-y y_0)))
                    x (apply range (vel-range vx [x_0 x_1] t))
                    y (apply range (vel-range vy [y_0 y_1] t))]
                [x y]))))
