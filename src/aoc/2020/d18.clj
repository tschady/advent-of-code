(ns aoc.2020.d18
  (:require [aoc.file-util :as file-util]
            [clojure.edn :as edn]
            [clojure.walk :as walk]))

(def input (file-util/read-lines "2020/d18.txt"))

(defn parse-line [s] (edn/read-string (str "(" s ")")))

(defn eval-1
  "Evaluate a mathematical expression from left to right, ignoring standard precedence rules."
  [[x & xs]]
  (reduce (fn [acc [op arg]] ((resolve op) acc arg))
          x
          (partition 2 xs)))

(defn eval-2
  "Evaluate a mathematical expression, giving precedence to addition over multiplication."
  [[x & xs]]
  (letfn [(delay-mul-eval [acc [[op arg] & rst]]
            (case op
              * (* acc (delay-mul-eval arg rst))
              + (recur (+ acc arg) rst)
              acc))]
    (delay-mul-eval x (partition 2 xs))))

(defn calc [eval-fn expr]
  (walk/postwalk #(cond->> % (list? %) eval-fn) expr))

(defn solve [input eval-fn]
  (->> input
       (map parse-line)
       (map (partial calc eval-fn))
       (reduce +' 0N)))

(defn part-1 [input] (solve input eval-1))

(defn part-2 [input] (solve input eval-2))
