(ns aoc.2021.d04
  (:require
   [aoc.file-util :as file-util]
   [clojure.core.matrix :refer [columns rows]]
   [com.rpl.specter :refer [ALL setval]]))

(def input (file-util/read-int-vectors "2021/d04.txt"))

(defn- parse-input [input] [(first input) (partition 5 (rest input))])

(defn- bingo? [card]
  (some #(every? nil? %) (concat (columns card) (rows card))))

(defn- score [card]
  (reduce + (remove nil? (flatten card))))

(defn- mark-all-cards
  "Returns `cards` coll with every instance of `num` replaced with 'nil'."
  [num cards]
  (setval [ALL ALL ALL #(= num %)] nil cards))

(defn scores [[[num & nums] cards]]
  (lazy-seq
   (when num
     (let [{winner true remain nil} (->> cards
                                         (mark-all-cards num)
                                         (group-by bingo?))]
       (if (not-empty winner)
         (cons (* num (score winner)) (scores [nums remain]))
         (scores [nums remain]))))))

(defn part-1 [input] (first (scores (parse-input input))))

(defn part-2 [input] (last (scores (parse-input input))))
