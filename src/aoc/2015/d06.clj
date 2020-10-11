(ns aoc.2015.d06
  (:require [aoc.file-util :as file-util]
            [aoc.grid :as grid]))

(def input (file-util/read-lines "2015/d06.txt"))

(def cmd-patt #"([a-z ]+) (\d+),(\d+) through (\d+),(\d+)")

(def grid-x 1000)

(defn- parse-line [line]
  (let [[_ cmd x1 y1 x2 y2] (re-find cmd-patt line)]
    [cmd
     [(Integer/parseInt x1) (Integer/parseInt y1)]
     [(Integer/parseInt x2) (Integer/parseInt y2)]]))

(defn light-commands [[cmd [x1 y1] [x2 y2]]]
  (for [x (range x1 (inc x2))
        y (range y1 (inc y2))
        :let [loc (grid/point->idx [x y] grid-x)]]
    [cmd loc]))

(defn change-light!
  "Mutates `lights` grid with command `cmd` at location `loc`.
  Cell can either be turned on (1), turned off (0), or toggled (1 <-> 0)."
  [lights [cmd loc]]
  (case cmd
    "turn on" (assoc! lights loc 1)
    "turn off" (assoc! lights loc 0)
    "toggle" (assoc! lights loc (if (= 1 (get lights loc)) 0 1))))

(defn change-light2!
  "Mutates `lights` grid with command `cmd` at location `loc`.
  Cell can be turned on (+1), turned off (-1, min 0), or toggled (+2)."
  [lights [cmd loc]]
  (let [cur (get lights loc 0)]
    (case cmd
      "turn on" (assoc! lights loc (inc cur))
      "turn off" (assoc! lights loc (max 0 (dec cur)))
      "toggle" (assoc! lights loc (+ 2 cur)))))

(defn solve
  "Returns total value of lights after applying instructions using
  light setting function `change-fn`."
  [lines change-fn]
  (let [commands (->> lines (map parse-line) (mapcat light-commands))]
    (->> (reduce change-fn (transient {}) commands)
         (persistent!)
         (vals)
         (reduce + 0))))

(defn part-1 [lines] (solve lines change-light!))

(defn part-2 [lines] (solve lines change-light2!))
