(ns aoc.2023.d08
  (:require
   [aoc.file-util :as f]
   [clojure.math.numeric-tower :refer [lcm]]
   [clojure.string :as str]
   [medley.core :refer [take-upto]]
   [plumbing.core :refer [for-map]]))

(def input (f/read-chunks "2023/d08.txt"))

(defn parse-net [s]
  (for-map [node (str/split-lines s)
            :let [[loc l r] (re-seq #"\w{3}" node)]]
           loc {\L l \R r}))

(defn next-loc [net [start dir]]
  [(get-in net [start (first dir)]) (rest dir)])

(defn steps-to-end [net end-fn inst start]
  (->> [start inst]
       (iterate (partial next-loc net))
       (map first)
       (take-upto end-fn)
       count
       dec))

(defn solve [input start-fn end-fn]
  (let [inst (cycle (first input))
        net (parse-net (second input))]
    (->> (start-fn net)
         (map (partial steps-to-end net end-fn inst))
         (reduce lcm))))

(defn starts [net] (filter #(str/ends-with? % "A") (keys net)))

(defn part-1 [input]
  (solve input (constantly ["AAA"]) #{"ZZZ"}))

(defn part-2 [input]
  (solve input starts #(str/ends-with? % "Z")))
