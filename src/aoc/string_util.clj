(ns aoc.string-util
  (:refer-clojure :exclude [ints])
  (:require [clojure.string :as str]))

(defn ints
  "Return a collection of integers found in a string.  Integers may be negative."
  [s]
  (map read-string (re-seq #"-?\d+" s)))

(defn explode-digits
  "Turn input string of digits into sequence of numbers they represent."
  [s]
  (map #(Character/getNumericValue %) s))

(def alphagram
  "The lowercased letters of a string arranged in alphabetical order"
  (comp sort str/lower-case))

(defn anagram?
  "Return true if the input strings `a` and `b` are non-identical,
  case-insensitive permutations of each other, else false."
  [a b]
  (and (not= (str/lower-case a) (str/lower-case b))
       (= (alphagram a) (alphagram b))))

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

(defn substring-replace
  "Replaces text from index `start` to `end` of `s` with `replacement`."
  [s [start end] replacement]
  (str (subs s 0 start) replacement (subs s end)))

(defn match-indices
  "Returns a coll of [start end] indices of all matches of `pattern` in `s`"
  [pattern s]
  (let [matcher (re-matcher (re-pattern pattern) s)]
    (for [x (repeatedly #(re-find matcher))
          :while (some? x)]
      [(.start matcher) (.end matcher)])))
