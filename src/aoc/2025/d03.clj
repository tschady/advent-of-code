(ns aoc.2025.d03
  (:require
   [aoc.file-util :as f]
   [aoc.string-util :as s]))

(def input (map s/explode-digits (f/read-lines "2025/d03.txt")))

(defn max-joltage
  ([pos bank] (max-joltage pos bank 0))
  ([pos bank sum]
   (if (zero? pos)
     sum
     (let [v     (apply max (drop-last (dec pos) bank))
           i     (.indexOf bank v)
           sum'  (+ (* sum 10) v)
           bank' (drop (inc i) bank)]
       (recur (dec pos) bank' sum')))))

(defn part-1 [input] (reduce + (map (partial max-joltage 2) input)))

(defn part-2 [input] (reduce + (map (partial max-joltage 12) input)))
