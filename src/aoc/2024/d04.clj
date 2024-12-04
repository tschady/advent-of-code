(ns aoc.2024.d04
  (:require
   [aoc.file-util :as f]
   [aoc.matrix :as mu :refer [flip-x get-rect]]
   [clojure.core.matrix :as mat]))

(def input (f/read-lines "2024/d04.txt"))

(defn make-matrix [strs] (mapv vec strs))

(defn diagonals [m]
  (let [size (count (first m))]
    (map (partial mat/diagonal m) (range (* -1 (dec size)) size))))

(defn rotations [m]
  (concat m (mat/transpose m) (diagonals m) (diagonals (flip-x m))))

(defn part-1 [input]
  (->> (make-matrix input)
       rotations
       (mapcat #(re-seq #"(?=(XMAS|SAMX))" (apply str %)))
       count))

(defn box-3 [m]
  (let [size (count m)]
    (for [y (range (- size 2))
          x (range (- size 2))]
      (apply str (flatten (get-rect m [x y] 3 3))))))

(defn part-2 [input]
  (->> (make-matrix input)
       box-3
       (keep #(re-find #"(M.M.A.S.S|M.S.A.M.S|S.M.A.S.M|S.S.A.M.M)" %))
       count))
