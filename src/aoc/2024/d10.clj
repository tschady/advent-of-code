(ns aoc.2024.d10
  (:require
   [aoc.file-util :as f]
   [aoc.grid :as grid]))

(def input (f/read-lines "2024/d10.txt"))

(defn paths [g head]
  (reduce (fn [locs target]
            (->> locs
                 (mapcat grid/neighbor-coords-news)
                 (filter #(= target (get g %)))))
          [head]
          (range 1 10)))

(defn solve [input f]
  (let [g (grid/build-grid input #(Character/getNumericValue %))]
    (transduce (map (comp count f (partial paths g))) + (grid/locate g 0))))

(defn part-1 [input] (solve input distinct))

(defn part-2 [input] (solve input identity))
