(ns aoc.string-util
  (:refer-clojure :exclude [ints])
  (:require
   [clojure.set :as set]
   [clojure.string :as str]))

(def alphabet-lower "abcdefghijklmnopqrstuvwxyz")
(def alphabet-upper "ABCDEFGHIJKLMNOPQRSTUVWXYZ")

(defn ints
  "Return a collection of integers found in a string.  Integers may be negative."
  [s]
  (map read-string (re-seq #"-?\d+" s)))

(defn ints-pos
  "Return a collection of positive integers found in a string.  Useful for strings with
  hyphens instead of negative signs."
  [s]
  (map read-string (re-seq #"\d+" s)))

(defn explode-digits
  "Turn input string of digits into sequence of numbers they represent."
  [s]
  (map #(Character/getNumericValue %) s))

(defn alphagram
  "The lowercased letters of a string arranged in alphabetical order"
  [s]
  (apply str (sort (str/lower-case (apply str s)))))

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

(defn s->int
  "Return the integer in `base` (default 10) represented by `s`, which may be
  a string or sequence of chars."
  ([xs] (s->int 10 xs))
  ([base xs] (Long/parseLong (apply str xs) base)))

(defn diff
  "Return a 3-tuple of:
  1- the chars in `a` that do not exist in `b`
  2- the chars in `b` that do not exist in `a`
  3- the chars common to `a` and `b`"
  [a b]
  (let [a (set a)
        b (set b)]
    [(set/difference a b)
     (set/difference b a)
     (set/intersection a b)]))

(defn halve
  "Returns a vector of 2 equal parts of `s`.  Extra chars will go at the end."
  [s]
  (split-at (quot (count s) 2) s))

(defn swap-letter [s x y] (replace {x y, y x} s))

(defn swap-pos [s i j] (swap-letter s (nth s i) (nth s j)))

(defn rotate-left [s n]
  (let [i (mod n (count s))]
    (concat (drop i s) (take i s))))

(defn rotate-right [s n]
  (let [i (mod n (count s))]
    (concat (take-last i s) (drop-last i s))))
