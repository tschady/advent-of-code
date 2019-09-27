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

(defn hamming-distance
  "The Hamming distance between two strings of equal length is the
  number of positions at which the corresponding symbols are different.
  Undefined (returns `nil`) for strings of unequal length."
  [a b]
  (when (= (count a) (count b))
    (count (filter false? (map = a b)))))

(defn string-intersection
  "Return the common letters between two input strings, where common
  letters must occur in the same corresponding position."
  [a b]
  (str/join (map #(when (= %1 %2) %1) a b)))
