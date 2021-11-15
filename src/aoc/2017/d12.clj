(ns aoc.2017.d12
  (:require [aoc.file-util :as file-util]
            [clojure.string :as str]
            [ubergraph.alg :as alg]
            [ubergraph.core :as uber]))

(def input (file-util/read-lines "2017/d12.txt"))

(defn parse-line [s] (read-string (str "{" (str/replace s #"<->" "[") "]}")))

(defn make-graph [lines] (uber/graph (apply merge (map parse-line lines))))

(defn find-groups [graph] (alg/connected-components graph))

(defn part-1 [input]
  (count (set (first (filter #(some #{0} %) (find-groups (make-graph input)))))))

(defn part-2 [input] (count (find-groups (make-graph input))))
