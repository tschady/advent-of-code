(ns aoc.2016.d01
  (:require [aoc.file-util :as file-util]
            [aoc.math-util :as math-util]
            [aoc.grid :as grid]))

(def input (file-util/read-file "2016/d01.txt"))

(defn part-1
  "Return Manhattan distance from origin determined by walking input."
  [input]
  (let [dest (last (grid/turns->path input))]
    (math-util/manhattan-dist grid/origin dest)))

(defn part-2
  "Return Manhattan distance from origin of first location reached twice
  by walking given input."
  [input]
  (let [locs (grid/turns->path input)
        dup (first (math-util/first-duplicate locs))]
    (math-util/manhattan-dist grid/origin dup)))
