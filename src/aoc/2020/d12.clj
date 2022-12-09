(ns aoc.2020.d12
  (:require [aoc.file-util :as file-util]
            [aoc.grid :as grid]
            [clojure.core.matrix :as mat]))

(def input (file-util/read-lines "2020/d12.txt"))

(defn rot-r [[x y]] [y (* -1 x)])

(defn rot-l [[x y]] [(* -1 y) x])

(defn num-turns [deg] (mod (quot deg 90) 4))

(defn sail
  "Return the ending cartesian coordinates reached by sailing via `input` instructions, and
  starting with heading `bearing`.
  Sailing may be done via two methods:
  1) simple, location based travel via `:loc`, where instructions move the ship directly
  or 2) waypoint based traveling via `:bearing`, where instructions move the heading marker and only
    forward travel is direct."
  [input method bearing]
  (:loc (reduce (fn [acc [dir mag]]
                    (condp = dir
                      \R (assoc acc :bearing (nth (iterate rot-r (get acc :bearing)) (num-turns mag)))
                      \L (assoc acc :bearing (nth (iterate rot-l (get acc :bearing)) (num-turns mag)))
                      \F (update acc :loc mat/add (mapv #(* mag %) (get acc :bearing)))
                      (update acc method mat/add (grid/move->delta [dir mag]))))
                  {:loc grid/origin :bearing bearing}
                  (map grid/parse-move input))))

(defn part-1 [input]
  (-> input
      (sail :loc [1 0])
      (grid/manhattan-dist grid/origin)))

(defn part-2 [input]
  (-> input
      (sail :bearing [10 1])
      (grid/manhattan-dist grid/origin)))
