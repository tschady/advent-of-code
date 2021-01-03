(ns aoc.2020.d24
  (:require [aoc.file-util :as file-util]
            [aoc.grid :as grid]
            [aoc.hex :as hex]
            [aoc.conway-life :as life]))

(def input (file-util/read-lines "2020/d24.txt"))

(defn steps->coord [s]
  (->> (re-seq #"(se|sw|nw|ne|w|e)" s)
       (map first)
       (reduce (fn [loc step] (grid/vector-add loc ((hex/delta loc) step)))
               grid/origin)))

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
  (-> (iterate (partial life/next-gen hex/neighbors #(<= 1 % 2) #(= 2 %)) (seed input))
      (nth 100)
      count))
