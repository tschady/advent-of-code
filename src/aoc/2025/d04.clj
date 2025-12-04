(ns aoc.2025.d04
  (:require
   [aoc.file-util :as f]
   [aoc.grid :as g]
   [medley.core :refer [remove-vals]]))

(def input (f/read-lines "2025/d04.txt"))

(defn grid [input] (remove-vals #{\.} (g/build-grid input identity)))

(defn forklift-step [[total-removed last-removed grid]]
  (let [next-remove (keys (filter #(> 4 (count (g/neighbors grid (first %)))) grid))]
    [(+ total-removed (count next-remove))
     total-removed
     (apply dissoc grid next-remove)]))

(defn solve [input stop-fn]
  (->> [0 nil (grid input)]
       (iterate forklift-step)
       stop-fn
       last
       first))

(defn part-1 [input] (solve input #(take 2 %)))

(defn part-2 [input] (solve input (fn [x] (take-while #(not= (first %) (second %)) x))))
