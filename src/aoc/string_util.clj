(ns aoc.string-util
  (:require [clojure.java.io :as io]
            [clojure.string :as str]))

(defn explode-digits
  "Turn input string of digits into sequence of numbers they represent."
  [s]
  (map #(Character/getNumericValue %) s))

(def alphagram (comp sort str/lower-case))

(defn anagram? [word candidate]
  (and (not= (str/lower-case word) (str/lower-case candidate))
       (= (alphagram word) (alphagram candidate))))
