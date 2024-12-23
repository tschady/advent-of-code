(ns aoc.2024.d23
  (:require
   [aoc.file-util :as f]
   [clojure.math.combinatorics :as combo]
   [clojure.string :as str]
   [ubergraph.alg :as alg]
   [ubergraph.core :as uber]))

(def input (f/read-file "2024/d23.txt"))

(defn parse [s] (map #(str/split % #"-") (str/split-lines s)))

(defn cliques [input]
  (alg/maximal-cliques (apply uber/graph (parse input))))

(defn part-1 [input]
  (->> (cliques input)
       (mapcat #(combo/combinations % 3))
       set
       (filter (partial some #(str/starts-with? % "t")))
       count))

(defn part-2 [input]
  (->> (cliques input)
       (apply max-key count)
       sort
       (str/join ",")))
