(ns aoc.2021.d04
  (:require
   [aoc.file-util :as file-util]
   [clojure.core.matrix :refer [columns rows]]
   [com.rpl.specter :refer [ALL setval]]))

(def input (file-util/read-int-vectors "2021/d04.txt"))

(defn- parse-input [input] [(first input) (partition 5 (rest input))])

(defn- bingo? [card]
  (some #(every? nil? %) (concat (columns card) (rows card))))

(defn- score [num card]
  (* num (reduce + (remove nil? (flatten card)))))

(defn- mark-all-cards
  "Returns `cards` coll with every instance of `num` replaced with 'nil'."
  [num cards]
  (setval [ALL ALL ALL #(= num %)] nil cards))

;; ^:blog
;; I used 2D arrays for cards and `nil` to mark numbers.
;; I thought `(group-by bingo?)` worked well, but the main point of interest is
;; the lazy sequence of recursive calls to `scores`. This sets up the elegant
;; `part-1`, `part-2` solution, which reads like the problem description.

(defn ^:blog scores [[[num & nums] cards]]
  (lazy-seq
   (when num
     (let [{winners true remain nil} (->> cards
                                         (mark-all-cards num)
                                         (group-by bingo?))
           win-scores (map (partial score num) winners)]
       (concat win-scores (scores [nums remain]))))))

(defn ^:blog part-1 [input] (first (scores (parse-input input))))

(defn ^:blog part-2 [input] (last (scores (parse-input input))))
