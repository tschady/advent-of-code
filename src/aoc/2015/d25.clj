(ns aoc.2015.d25
  (:require [aoc.file-util :as file-util]))

(def input (file-util/read-file "2015/d25.txt"))

(defn input->coord [line] (mapv read-string (re-seq #"\d+" line)))

(def start-code 20151125)

(defn step [n] (rem (* n 252533) 33554393))

(defn coord->idx
  "Convert coord vector into its index, when numbers are laid out in a
  diagonal going up and to the right."
  [[row col]]
  (let [tier (+ row col -1)
        tier-base (reduce + 1 (range tier))
        tier-offset (dec col)]
    (+ tier-base tier-offset)))

(defn part-1 [input]
  (let [idx (-> input input->coord coord->idx)]
    (nth (iterate step start-code) (dec idx))))

;; no part-2!
