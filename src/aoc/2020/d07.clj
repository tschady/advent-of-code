(ns aoc.2020.d07
  (:require [aoc.file-util :as file-util]
            [loom.graph :as loom :refer [successors weight transpose nodes]]
            [loom.derived :refer [subgraph-reachable-from]]))

(def input (file-util/read-lines "2020/d07.txt"))

(defn parse-rule [s]
  (when (not (re-find #"no other" s))
    (let [[[_ parent] & inner] (map rest (re-seq #"(\d+)? ?(\w+ \w+) bag" s))
          children             (->> inner
                                    (map #(hash-map (second %) (Integer/parseInt (first %))))
                                    (apply merge))]
      {parent children})))

(defn build-graph [rules]
  (apply loom/weighted-digraph (keep parse-rule rules)))

(defn nested-count [g node]
  (reduce + 1 (map #(* (weight g node %) (nested-count g %)) (successors g node))))

(defn part-1 [input]
  (-> input build-graph transpose (subgraph-reachable-from "shiny gold") nodes count dec))

(defn part-2 [input]
  (-> input build-graph (nested-count "shiny gold") dec))
