(ns aoc.2016.d01
  (:require [aoc.coll-util :refer [first-duplicate]]
            [aoc.file-util :as file-util]
            [aoc.grid :as grid]))

(def input (first (file-util/read-csv "2016/d01.txt")))

(defn part-1
  "Return Manhattan distance from origin determined by walking input."
  [input]
  (let [dest (last (grid/turns->path input))]
    (grid/manhattan-dist grid/origin dest)))

(defn part-2
  "Return Manhattan distance from origin of first location reached twice
  by walking given input."
  [input]
  (let [locs (grid/turns->path input)
        dup (first (first-duplicate locs))]
    (grid/manhattan-dist grid/origin dup)))
