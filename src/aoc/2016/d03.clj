(ns aoc.2016.d03
  (:require [aoc.file-util :as file-util]))

(defn parse-triangle [line]
  (read-string (str "[" line "]")))

(def input (map parse-triangle (file-util/read-lines "2016/d03.txt")))

(defn valid-triangle? [[a b c]]
  (and (> (+ a b) c)
       (> (+ b c) a)
       (> (+ c a) b)))

(defn solve [triangles]
  (count (filter valid-triangle? triangles)))

(defn part-1 [input] (solve input))

(defn part-2 [input]
  (let [triangles (->> input
                       (apply mapcat list)
                       (partition 3))]
    (solve triangles)))
