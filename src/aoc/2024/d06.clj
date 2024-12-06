(ns aoc.2024.d06
  (:require
   [aoc.coll-util :as c]
   [aoc.file-util :as f]
   [aoc.grid :as grid]))

(def input (f/read-lines "2024/d06.txt"))

(defn walk-route [g]
  (loop [pos      (first (grid/locate g :guard))
         bearings (cycle '([0 -1] [1 0] [0 1] [-1 0]))
         seen     #{}]
    (let [heading (first bearings)
          pos'    (map + pos heading)]
      (cond
        (not (contains? g pos)) ;; out of bounds
        {:completed (set (map first seen))}

        (contains? seen [pos heading]) ;; loop detected
        {:loop seen}

        (= :block (get g pos')) ;; hit an obstacle
        (recur pos (rest bearings) (conj seen [pos heading]))

        :else ;; keep er movin
        (recur pos' bearings (conj seen [pos heading]))))))

(defn part-1 [input]
  (-> input
      (grid/build-grid {\^ :guard \# :block \. :open})
      walk-route
      :completed
      count))

(defn part-2 [input]
  (let [g (-> input (grid/build-grid {\^ :guard \# :block \. :open}))]
    (->> (walk-route g)
         :completed
         (pmap (fn [loc] (:loop (walk-route (assoc g loc :block)))))
         (c/count-truthy))))
