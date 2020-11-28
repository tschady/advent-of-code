(ns aoc.2018.d07
  (:require [aoc.file-util :as file-util]
            [com.rpl.specter :as specter :refer [transform MAP-VALS]]
            [ubergraph.core :as uber :refer [digraph in-degree nodes remove-nodes add-attr]]))

(def input (file-util/read-lines "2018/d07.txt"))

(defn parse-edge [s] (into [] (rest (re-find #"Step (\w) .* step (\w)" s))))

(defn build-graph [instrs] (apply digraph (map parse-edge instrs)))

(defn busy-nodes [g] (->> (:attrs g)
                          (filter #(contains? (val %) :timer))
                          (map first)))

(defn completed-nodes [g] (->> (:attrs g)
                               (filter #(zero? (:timer (second %))))
                               (map first)))

(defn available-nodes [g]
  (->> (nodes g)
       (remove (set (busy-nodes g)))
       (filter #(zero? (in-degree g %)))
       sort))

(defn step-order [g]
  (loop [g g
         order ""]
    (if-let [next-node (first (available-nodes g))]
      (recur (remove-nodes g next-node) (str order next-node))
      order)))

(defn tick-timers [g]
  (transform [:attrs MAP-VALS :timer] dec g))

(defn complete-work [g]
  (apply remove-nodes g (completed-nodes g)))

(defn work-time
  "Return time (secs) to build node `s`."
  [s base-time]
  (+ base-time 1 (- (int (first s)) (int \A))))

(defn assign-work [g num-workers base-time]
  (let [num-free-workers (- num-workers (count (busy-nodes g)))
        new-nodes-to-work (take num-free-workers (available-nodes g))]
    (reduce (fn [g node] (add-attr g node :timer (work-time node base-time)))
            g
            new-nodes-to-work)))

(defn time-to-build [g num-workers base-time]
  (loop [t -1
         g g]
    (if (nodes g)
      (recur (inc t)
             (-> g
                 tick-timers
                 complete-work
                 (assign-work num-workers base-time)))
      t)))

(defn part-1 [input] (step-order (build-graph input)))

(defn part-2 [input] (time-to-build (build-graph input) 5 60))
