(ns aoc.2024.d08
  (:require
   [aoc.file-util :as f]
   [aoc.grid :as grid]
   [clojure.math.combinatorics :as combo]))

(def input (f/read-lines "2024/d08.txt"))

(defn antinodes [g a b]
  (let [delta (map - a b)]
    (filter (partial contains? g) (list (mapv + a delta) (mapv - b delta)))))

(defn antinodes2 [g a b]
  (let [delta (map - a b)]
    (concat (take-while (partial contains? g) (iterate #(mapv + % delta) a))
            (take-while (partial contains? g) (iterate #(mapv - % delta) b)))))

(defn find-antinodes [detect-fn g locs]
  (mapcat (partial apply (partial detect-fn g)) (combo/combinations locs 2)))

(defn solve [detect-fn input]
  (let [g        (grid/build-grid input identity)
        antennas (map keys (map second (dissoc (group-by val g) \.)))]
    (->> antennas
         (mapcat (partial find-antinodes detect-fn g))
         (into #{})
         count)))

(defn part-1 [input] (solve antinodes input))

(defn part-2 [input] (solve antinodes2 input))
