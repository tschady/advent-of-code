(ns aoc.2015.d20
  (:require [aoc.file-util :as file-util]
            [aoc.math-util :as math-util]))

(def input (first (file-util/read-values "2015/d20.txt")))

(defn- score [mult elves]
  (reduce + 0 (map #(* mult %) elves)))

(defn- elves
  "Return set of elf numbers that visit `house`.  Elves visit each
  house evenly divisible by itself, up to `limit` deliveries."
  [limit house]
  (remove #(> house (* limit %)) (math-util/factors house)))

(defn- solve
  "Returns lowest house number of house to get at least `present-target`."
  [present-target score-mult elf-limit]
  (->> (range)
       (map (partial elves elf-limit))
       (map (partial score score-mult))
       (take-while (partial > present-target))
       count))

(defn part-1 [input] (solve input 10 Integer/MAX_VALUE))

(defn part-2 [input] (solve input 11 50))
