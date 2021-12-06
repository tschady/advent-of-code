(ns aoc.2021.d06
  (:require [aoc.file-util :as f]))

(def input (f/read-ints "2021/d06.txt"))

(defn init-state [input]
  (mapv #(get (frequencies input) % 0) (range 9)))

(defn next-gen [timers]
  (let [spawners (get timers 0 0)]
    (-> timers
        (subvec 1)
        (assoc 8 spawners)
        (update 6 (fnil + 0) spawners))))

(defn solve [input gens]
  (reduce + (nth (iterate next-gen (init-state input)) gens)))

(defn part-1 [input] (solve input 80))

(defn part-2 [input] (solve input 256))
