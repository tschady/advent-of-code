(ns aoc.2015.d16
  (:require [aoc.file-util :as file-util]
            [aoc.coll-util :as coll-util]
            [clojure.string :as str]))

(def input (file-util/read-lines "2015/d16.txt"))

(def target-traits
  {:children 3
   :cats 7
   :samoyeds 2
   :pomeranians 3
   :akitas 0
   :vizslas 0
   :goldfish 5
   :trees 3
   :cars 2
   :perfumes 1})

(def target-ranges
  {:children    #(= % 3)
   :cats        #(> % 7)
   :samoyeds    #(= % 2)
   :pomeranians #(< % 3)
   :akitas      #(= % 0)
   :vizslas     #(= % 0)
   :goldfish    #(< % 5)
   :trees       #(> % 3)
   :cars        #(= % 2)
   :perfumes    #(= % 1)})

(defn- parse-line
  "Return a map of keywords and integer values of traits for an Aunt."
  [line]
  (->> (str/replace line ":" "")
       (re-seq #"(\w+) (\d+)")
       (map (fn [[_ k v]] [(keyword k) (Integer/parseInt v)]))
       (into {})))

(defn part-1
  "Find the Aunt whose traits are an exact subset of the target traits."
  [input]
  (->> input
       (map parse-line)
       (filter #(coll-util/submap? (dissoc % :Sue) target-traits))
       (map :Sue)
       first))

(defn- match-ranges?
  "Return true if every val in map `candidate` satisfies the lambda
  in `target` map for same key."
  [target candidate]
  (every? (fn [[k v]] ((get target k) v)) candidate))

(defn part-2
  "Find the Aunt whose traits fit the ranges specified in target-ranges."
  [input]
  (->> input
       (map parse-line)
       (filter #(match-ranges? target-ranges (dissoc % :Sue)))
       (map :Sue)
       first))
