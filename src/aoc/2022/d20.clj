(ns aoc.2022.d20
  (:require
   [aoc.file-util :as f]
   [aoc.list :as list]))

(def input (f/read-ints "2022/d20.txt"))

(defn mix! [nodes]
  (let [len (count nodes)]
    (doseq [node nodes]
      (list/shift-right node (mod (list/value node) (dec len))))
    nodes))

(defn grove-coords [zero-node]
  (let [vals (map list/value (iterate list/get-next zero-node))]
    (->> [1000 2000 3000]
         (map #(nth vals %))
         (apply +))))

(defn solve [data mix-count decrypt-key]
  (let [zero-idx (.indexOf data 0)
        nodes    (list/make-circ-list (map #(* decrypt-key %) data))]
    (dorun (repeatedly mix-count #(mix! nodes)))
    (grove-coords (nth nodes zero-idx))))

(defn part-1 [input] (solve input 1 1))

(defn part-2 [input] (solve input 10 811589153))
