(ns aoc.2021.d01
  (:require [aoc.file-util :as file-util]))

(def input (file-util/read-ints "2021/d01.txt"))

(defn part-1 [input]
  (count (filter pos? (map - (rest input) input))))

(defn part-2 [input]
  (count (filter pos? (map - (drop 3 input) input))))
