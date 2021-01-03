(ns aoc.2020.d17
  (:require [aoc.conway-life :as life]
            [aoc.file-util :as file-util]
            [aoc.grid :as grid]
            [medley.core :as medley :refer [remove-vals]]))

(def input (file-util/read-lines "2020/d17.txt"))

(defn seed [dim input]
  (->> (grid/build-grid input {\. nil, \# :active})
       (remove-vals nil?)
       keys
       (map #(apply conj % (repeat (- dim 2) 0)))))

(defn solve [input dim neighbor-fn]
  (-> (iterate (partial life/next-gen neighbor-fn #(<= 2 % 3) #(= 3 %)) (seed dim input))
      (nth 6)
      count))

(defn part-1 [input] (solve input 3 grid/neighbor-coords-3d))

(defn part-2 [input] (solve input 4 grid/neighbor-coords-4d))
