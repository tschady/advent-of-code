(ns aoc.2020.d17
  (:require [aoc.file-util :as file-util]
            [aoc.grid :as grid]
            [medley.core :as medley :refer [remove-vals filter-vals]]
            [clojure.set :as set]))

(def input (file-util/read-lines "2020/d17.txt"))

(defn initial-active [dim input]
  (->> (grid/build-grid input {\. nil, \# :active})
       (remove-vals nil?)
       keys
       (map #(apply conj % (repeat (- dim 2) 0)))))

(defn step-world [neighbor-fn actives]
  (let [half
        (reduce (fn [acc active-loc]
                  (let [neighbors          (set (neighbor-fn active-loc))
                        active-neighbors   (set/intersection neighbors (set actives))
                        inactive-neighbors (set/difference neighbors active-neighbors)]
                    (-> (if (<= 2 (count active-neighbors) 3)
                          (update-in acc [:active] conj active-loc)
                          acc)
                        (update-in [:inactive] #(merge-with + % (frequencies inactive-neighbors))))))
                {}
                actives)]
    (concat (:active half) (keys (filter-vals #(= 3 %) (:inactive half))))))

(defn solve [input dim neighbor-fn]
  (-> (iterate (partial step-world neighbor-fn) (initial-active dim input))
      (nth 6)
      count))

(defn part-1 [input] (solve input 3 grid/neighbor-coords-3d))

(defn part-2 [input] (solve input 4 grid/neighbor-coords-4d))
