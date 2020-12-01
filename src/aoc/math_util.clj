(ns aoc.math-util
  (:require [clojure.string :as str]))

(defn factors
  "Return vector of all factors of a given integer `n`"
  [n]
  (->> (range 1 (inc (Math/sqrt n)))
       (filter #(zero? (rem n %)))
       (mapcat #(vector % (/ n %)))
       (into (sorted-set))))

(defn digits->num
  "Given a sequence of digits, join them together into one integer."
  [xs]
  (->> xs (map str) str/join read-string))
