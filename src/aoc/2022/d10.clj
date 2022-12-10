(ns aoc.2022.d10
  (:require
   [aoc.file-util :as f]
   [clojure.string :as str]))

(def input (f/read-file "2022/d10.txt"))

(defn reg-series [input]
  (let [munged (str/replace input #"(noop|addx)" "0")
        deltas (read-string (str "[ 1 " munged " ]"))]
    (reductions + deltas)))

(defn part-1 [input]
  (->> [20 60 100 140 180 220]
       (map #(* % (nth (reg-series input) (dec %))))
       (reduce +)))

(defn part-2 [input]
  (->> (reg-series input)
       (map-indexed #(if (<= -1 (- (mod %1 40) %2) 1) \# \.))
       (partition 40)
       (map (partial apply str))))
