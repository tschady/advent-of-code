(ns aoc.2020.d24
  (:require [aoc.file-util :as file-util]
            [aoc.hex :as hex]
            [aoc.conway-life :as life]
            [aoc.math-util :as math-util]))

(def input (file-util/read-lines "2020/d24.txt"))

(defn steps->coord [s]
  (->> (re-seq #"(se|sw|nw|ne|w|e)" s)
       (map first)
       (map (partial hex/delta :pointy-top))
       (math-util/vector-math +)))

(defn seed
  "Returns initial set of black tile coordinates from given instructions."
  [input]
  (->> (map steps->coord input)
       (reduce (fn [acc loc]
                 (if (contains? acc loc)
                   (disj acc loc)
                   (conj acc loc)))
               #{})
       (into [])))

(defn part-1 [input] (count (seed input)))

(defn part-2 [input]
  (-> (partial life/next-gen
               (partial hex/neighbors :pointy-top)
               #(<= 1 % 2)
               #(= 2 %))
      (iterate (seed input))
      (nth 100)
      count))
