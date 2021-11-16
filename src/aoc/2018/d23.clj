(ns aoc.2018.d23
  (:require [aoc.file-util :as file-util]
            [aoc.grid :as grid]
            [aoc.string-util :as string-util]))

(def input (file-util/read-lines "2018/d23.txt"))

(defn- parse-bot
  "Given an input string of nanobot parameters, parse into hashmap."
  [bot-str]
  (let [[x y z r] (string-util/ints bot-str)]
    {:loc [x y z] :r r}))

(defn- within-range?
  "Predicate function returning whether the candidate bot is within
  the subject bot's range, inclusively."
  [subject candidate]
  (>= (:r subject)
      (grid/manhattan-dist (:loc candidate) (:loc subject))))

(defn part-1
  "Calculate the number of bots within manhattan-distance of the nanobot
  with the largest range."
  [input]
  (let [bots (map parse-bot input)
        big-bot (apply max-key :r bots)]
    (count (filter (partial within-range? big-bot) bots))))

#_(defn part-2
  "Return the manhattan distance from the origin to the closest coordinates
  that are in range of the largest number of nanobots."
  [input]
  true)
