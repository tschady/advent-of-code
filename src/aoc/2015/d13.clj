(ns aoc.2015.d13
  (:require [aoc.file-util :as file-util]
            [clojure.math.combinatorics :as combo]
            [clojure.set :as set]))

(def input (file-util/read-lines "2015/d13.txt"))

(def data-pattern #"^(\w+) would (gain|lose) (\d+) happiness units by sitting next to (\w+).$")

(defn- parse-input-line [s]
  (let [[_ a sign-str amt b] (re-find data-pattern s)
        sign (if (= "lose" sign-str) -1 1)]
    {#{a b} (* sign (Integer/parseInt amt))}))

(defn- seat-scores
  "Return map of pairings to score.  Pairings denote table neighbors
  e.g. #{Alice Bob} 127"
  [coll]
  (apply merge-with + (map parse-input-line coll)))

(defn- diners
  "Return list of all diners in the guest list."
  [graph]
  (->> graph keys (apply set/union) vec))

(defn- pairings
  "Given ordered circular sequence of guests, return all neighbor pairings."
  [xs]
  (map set (partition 2 1 (vector (first xs)) xs)))

(defn- solve
  "Return max happiness score based on optimal configuration of `guests`
  given happiness score pairings given by `scores`."
  [guests scores]
  (->> (combo/permutations guests)
       (map pairings)
       (map (partial keep scores))
       (map (partial reduce + 0))
       (apply max)))

(defn part-1
  "Return max happiness score for given guest list and scores."
  [coll]
  (let [scores (seat-scores coll)
        guests (diners scores)]
    (solve guests scores)))

(defn part-2
  "Return max happiness score for given guest list and scores,
  including 'myself' as a guest of neutral pairwise happiness."
  [coll]
  (let [scores (seat-scores coll)
        guests (conj (diners scores) :me)]
    (solve guests scores)))
