(ns aoc.2024.d03
  (:require
   [aoc.file-util :as f]
   [aoc.string-util :as s]
   [clojure.string :as str]))

(def input (f/read-file "2024/d03.txt"))

(defn clean [s] (str/replace s #"(?s)don't\(\).*?(:?do\(\)|\Z)" ""))

(defn execute [mem]
  (transduce (comp (map second) (map s/ints) (map #(apply * %)))
             +
             (re-seq #"mul\((\d{1,3},\d{1,3})\)" mem)))

(defn part-1 [input] (-> input execute))

(defn part-2 [input] (-> input clean execute))
