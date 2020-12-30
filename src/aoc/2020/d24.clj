(ns aoc.2020.d24
  (:require [aoc.file-util :as file-util]
            [aoc.grid :as grid]
            [aoc.hex :as hex]
            [clojure.set :as set]
            [medley.core :refer [filter-vals]]))

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

(defn next-gen [actives]
  (let [half (reduce (fn [acc active-loc]
                       (let [neighbors          (set (hex/neighbors active-loc))
                             active-neighbors   (set/intersection neighbors (set actives))
                             inactive-neighbors (set/difference neighbors active-neighbors)]
                         (-> (if (<= 1 (count active-neighbors) 2)
                               (update-in acc [:active] conj active-loc)
                               acc)
                             (update-in [:inactive] #(merge-with + % (frequencies inactive-neighbors))))))
                     {}
                     actives)]
    (concat (:active half) (keys (filter-vals #(= 2 %) (:inactive half))))))

(defn part-1 [input] (count (seed input)))

(defn part-2 [input]
  (-> (iterate next-gen (seed input))
      (nth 100)
      count))
