(ns aoc.2024.d05
  (:require
   [aoc.coll-util :as c :refer [midpoint]]
   [aoc.file-util :as f]
   [aoc.string-util :as s]
   [clojure.set :as set]
   [clojure.string :as str]))

(def input (f/parse-chunks "2024/d05.txt" [[:rules parse-rules]
                                           [:updates parse-updates]]))

(defn parse-rules [s] (->> (s/ints s)
                           (partition 2)
                           (map (fn [[k v]] {k #{v}}))
                           (apply merge-with into)))

(defn parse-updates [s] (mapv (comp vec s/ints) (str/split-lines s)))

(defn by-rules-order [rules a b]
  (if (contains? (rules b) a) 1 -1))

(defn valid? [rules update]
  (= update (sort (partial by-rules-order rules) update)))

(defn part-1 [{:keys [rules updates]}]
  (->> updates
       (filter (partial valid? rules))
       (map midpoint)
       (reduce +)))

(defn part-2 [{:keys [rules updates]}]
  (->> updates
       (remove (partial valid? rules))
       (map #(sort (partial by-rules-order rules) %))
       (map midpoint)
       (reduce +)))
