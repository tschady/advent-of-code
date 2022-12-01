(ns aoc.2022.d01
  (:require [aoc.file-util :as file-util]
            [aoc.string-util :as string-util]))

(def input (map string-util/ints (file-util/read-chunks "2022/d01.txt")))

(defn part-1 [data]
  (->> data
       (map (partial reduce +))
       (reduce max)))

(defn part-2 [data]
  (->> data
       (map (partial reduce +))
       (sort >)
       (take 3)
       (reduce +)))
