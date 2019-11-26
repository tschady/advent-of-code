(ns aoc.2015.d06
  (:require [aoc.file-util :as file-util]))

(def input (file-util/read-lines "2015/d06.txt"))

(def grid-x 1000)
(def grid-y 1000)
(def grid-size (* grid-x grid-y))

(def cmd-patt #"([a-z ]+) (\d+),(\d+) through (\d+),(\d+)")

(defn- parse-line [line]
  (let [[_ cmd x1 y1 x2 y2] (re-find cmd-patt line)]
    [cmd
     [(Integer/parseInt x1) (Integer/parseInt y1)]
     [(Integer/parseInt x2) (Integer/parseInt y2)]]))

(defn- location [x y] (+ y (* x grid-x)))

(defn light-commands
  ""
  [[cmd [x1 y1] [x2 y2]]]
  (for [x (range x1 (inc x2))
        y (range y1 (inc y2))
        :let [loc (location x y)]]
    [cmd loc]))

(defn change-light!
  ""
  [lights [cmd loc]]
  (case cmd
    "turn on" (aset-boolean lights loc true)
    "turn off" (aset-boolean lights loc false)
    "toggle" (aset-boolean lights loc (not (aget lights loc))))
  lights)

(defn change-light2!
  ""
  [lights [cmd loc]]
  (let [cur (aget lights loc)]
    (case cmd
      "turn on" (aset-int lights loc (inc cur))
      "turn off" (aset-int lights loc (max 0 (dec cur)))
      "toggle" (aset-int lights loc (+ 2 cur))))
  lights)

(defn part-1
  ""
  [lines]
  (let [lights (boolean-array grid-size)]
    (->> lines
         (map parse-line)
         (mapcat light-commands)
         (reduce change-light! lights)
         (vec)
         (filter true?)
         count)))

(defn part-2
  ""
  [lines]
  (let [lights (int-array grid-size)]
    (->> lines
         (map parse-line)
         (mapcat light-commands)
         (reduce change-light2! lights)
         (vec)
         (reduce + 0))))
