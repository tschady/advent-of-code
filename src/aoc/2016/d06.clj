(ns aoc.2016.d06
  (:require [aoc.file-util :as file-util]))

(def input (file-util/read-lines "2016/d06.txt"))

(defn solve [input sort-dir]
  (->> input
       (apply map list)
       (map frequencies)
       (map (partial sort-by val sort-dir))
       (map ffirst)
       (apply str)))

(defn part-1 [input]
  (solve input >))

(defn part-2 [input]
  (solve input <))
