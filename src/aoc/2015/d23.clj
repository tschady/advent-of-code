(ns aoc.2015.d23
  (:require [aoc.file-util :as file-util]))

(def input (file-util/read-lines "2015/d23.txt"))

(defn parse-inst [s]
  (let [[_ op arg1 arg2] (re-matches #"(\w+) ([+-]?\w+)(?:, )?([-+]?\w+)?" s)
        arg1 (if (= op "jmp") (Integer/parseInt arg1) arg1)
        arg2 (if arg2 (Integer/parseInt arg2) nil)]
    [op arg1 arg2]))

(defn run [prog mem]
  (loop [mem mem
         ptr 0]
    (let [[op arg1 arg2] (nth prog ptr)
          [mem ptr] (case op
                        "hlf" [(update mem arg1 / 2) (inc ptr)]
                        "tpl" [(update mem arg1 * 3) (inc ptr)]
                        "inc" [(update mem arg1 inc) (inc ptr)]
                        "jmp" [mem (+ ptr arg1)]
                        "jie" [mem (if (even? (get mem arg1))
                                       (+ ptr arg2)
                                       (inc ptr))]
                        "jio" [mem (if (= 1 (get mem arg1))
                                       (+ ptr arg2)
                                       (inc ptr))])]
      (if (< -1 ptr (count prog))
        (recur mem ptr)
        mem))))

(defn part-1 [input] (get (run (map parse-inst input) {"a" 0, "b" 0}) "b"))

(defn part-2 [input] (get (run (map parse-inst input) {"a" 1, "b" 0}) "b"))
