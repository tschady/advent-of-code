(ns aoc.2019.d04
  (:require [aoc.string-util :as string-util]
            [clojure.spec.alpha :as s]
            [clojure.string :as str]
            [aoc.math-util :as math-util]))

(def input-min 402328)
(def input-max 864247)

(s/def ::digits-increasing #(apply <= %))
(s/def ::digits-6 #(= 6 (count %)))
(s/def ::digits-have-dupe #(< (count (dedupe %)) 6))
(s/def ::digits-have-1-double #(some (partial = 2) (map count (partition-by identity %))))

(s/def ::pass1 (s/and ::digits-increasing
                      ::digits-6
                      ::digits-have-dupe))

(s/def ::pass2 (s/and ::pass1 ::digits-have-1-double))

(defn solve [spec input-min input-max]
  (->> (range input-min (inc input-max))
     (filter #(s/valid? spec (string-util/explode-digits (str %))))
     count))

(defn part-1 [input-min input-max] (solve ::pass1 input-min input-max))

(defn part-2 [input-min input-max] (solve ::pass2 input-min input-max))
