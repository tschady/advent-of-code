(ns aoc.2018.d05
  (:require [aoc.file-util :as file-util]
            [clojure.string :as str]))

(def input (file-util/read-file "2018/d05.txt"))

(defn- react?
  "Two units react if they're the same type (letter), but different polarity (case).  `nil` inputs do not react with anything."
  [a b]
  (and (and (some? a) (some? b))
       (not= a b)
       (= (str/lower-case a) (str/lower-case b))))

(defn alchemize
  "Squeeze the input polymer by removing reacting pairs with one pass."
  [polymer]
  (reduce (fn [existing-chain next-unit]
            (if (react? (last existing-chain) next-unit)
              (pop existing-chain)
              (conj existing-chain next-unit)))
          []
          polymer))

(defn part-1
  "Determine the length of polymer after reaction."
  [polymer]
  (count (alchemize polymer)))

(defn get-units
  "Return set of all units in input polymer, normalized to lowercase."
  [polymer]
  (set (map str/lower-case polymer)))

(defn strip-unit
  "Remove all variations of unit from polymer."
  [polymer unit]
  (remove #(= (str/lower-case %) (str unit)) polymer))

(defn part-2
  "Determine shortest possible post-reaction polymer, given the removal
  of any one unit from input polymer."
  [polymer]
  (->> (get-units polymer)
       (map #(strip-unit polymer %))
       (map alchemize)
       (map count)
       (apply min)))
