(ns aoc.2015.d05
  (:require [aoc.file-util :as file-util]))

(def input (file-util/read-lines "2015/d05.txt"))

(defn nice?
  "Return true if a word is nice, else false.
  A word is nice if it has:
   - at least 3 vowels
   - at least one consecutive repeated letter
   - NONE of the following substrings: 'ab', 'cd', 'pq', 'xy'"
  [s]
  (and (>= (count (filter #{\a \e \i \o \u} s)) 3)
       (< (count (dedupe s)) (count s))
       (nil? (re-find #"ab|cd|pq|xy" s))))

(defn nice2?
  "Return true if a word is nice2, else false.
  A word is nice if:
  - It contains a pair of any two letters that appears at least twice
    in the string without overlapping, like xyxy (xy) or aabcdefgaa (aa),
    but not like aaa (aa, but it overlaps)
  - It contains at least one letter which repeats with exactly one letter
    between them, like xyx, abcdefeghi (efe), or even aaa."
  [s]
  (boolean (and (re-find #"([A-Za-z][A-Za-z]).*\1" s)
                (re-find #"([A-Za-z]).\1" s))))

(defn part-1
  "Return number of nice words in list."
  [words]
  (count (filter nice? words)))

(defn part-2
  "Return number of nice2 words in list."
  [words]
  (count (filter nice2? words)))
