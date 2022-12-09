(ns aoc.2022.d09
  (:require
   [aoc.coll-util :refer [x-nth]]
   [aoc.file-util :as f]
   [aoc.grid :as grid]
   [clojure.core.matrix :as m]))

(def input (f/read-lines "2022/d09.txt"))

(defn touching?
  "Returns true if given points are adjacent, including diagonally."
  [a b]
  (every? #(<= -1 % 1) (m/sub a b)))

(defn step-tail
  "Returns new coordinate of tail `t`, which moves at most one square in
  each direction towards the head `h`."
  [t h]
  (if (touching? t h)
    t
    (-> (m/sub h t) (m/clamp -1 1) (m/add t))))

(defn solve
  "Returns the number of distinct coords the n-th knot passes through."
  [n input]
  (->> (grid/moves->path input)
       (iterate (partial reductions step-tail grid/origin))
       (x-nth n)
       distinct
       count))

(defn part-1 [input] (solve 1 input))

(defn part-2 [input] (solve 9 input))
