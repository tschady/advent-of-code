(ns aoc.2022.d03
  (:require
   [aoc.file-util :as f]
   [aoc.string-util :as s]
   [clojure.set :as set]))

(def input (f/read-lines "2022/d03.txt"))

(def priority (zipmap (str s/alphabet-lower s/alphabet-upper) (range 1 53)))

(defn common-item [sacks]
  (first (apply set/intersection (map set sacks))))

(defn solve [data split-fn]
  (->> (split-fn data)
       (map common-item)
       (map priority)
       (reduce +)))

(defn part-1 [input] (solve input (partial map s/halve)))

(defn part-2 [input] (solve input (partial partition 3)))
