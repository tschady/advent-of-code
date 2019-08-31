(ns aoc.2015.d08
  (:require [aoc.file-util :as file-util]
            [clojure.string :as str]))

(def input (file-util/read-lines "2015/d08.txt"))

(defn decode-count
  [s]
  (-> s
      (str/replace #"\\\\" ".")
      (str/replace #"\\x([0-9a-f]{1,2})" ".")
      read-string
      count))

(defn recode-count
  [s]
  (-> s
      (str/replace #"[\\\"]" "..")
      count
      (+ 2)))

(defn solve [f input] (reduce + (map #(- (count %) (f %)) input)))

(defn part-1 [input] (solve decode-count input))
(defn part-2 [input] (solve recode-count input))
