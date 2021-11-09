(ns aoc.2020.d21
  (:refer-clojure :exclude [==])
  (:require [aoc.file-util :as file-util]
            [clojure.core.logic :as logic :refer :all]
            [clojure.string :as str]
            [clojure.set :as set]))

(def input (file-util/read-lines "2020/d21.txt"))

(defn parse-line
  "Returns map of allergen to set of all ingredients for the food item the allergen occurs in."
  [line]
  (let [[ingreds allergens] (->> (str/split line #"\(contains")
                                 (map #(map first (re-seq #"(\w+)" %))))]
    (for [allergen allergens]
      {allergen (set ingreds)})))

(defn freq-db [input]
  (->> (str/replace (str/join "\n" input) #"\(contains.*\)" "")
     (re-seq #"\w+")
     frequencies))

(defn poss-db
  "Returns map of allergen names to the set of possibly matching ingredients."
  [input]
  (->> input
       (mapcat parse-line)
       (apply merge-with set/intersection)))

(defn allergen->ingred [input]
  (first
   (let [db      (poss-db input)
         results (zipmap (keys db) (repeatedly lvar))]
     (run* [q]
       (distincto (vals results))
       (everyg (fn [[k v]] (membero (get results k) (vec v))) db)
       (== q results)))))

(defn part-1 [input]
  (let [allergens (vals (allergen->ingred input))]
    (reduce + (vals (apply dissoc (freq-db input) allergens)))))

(defn part-2 [input]
  (str/join "," (vals (sort (allergen->ingred input)))))
