(ns aoc.2015.d01
  (:require [aoc.file-util :as file-util]))

(def input (file-util/read-file "2015/d01.txt"))

(def travel {\( 1, \) -1})

(defn part-1
  "Determine end floor of up/down elevator instruction series."
  [input]
  (reduce + (map travel input)))

(defn part-2
  "Find the number of the step that first takes us to floor -1"
  [input]
  (inc (.indexOf (reductions + (map travel input)) -1)))
