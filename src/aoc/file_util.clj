(ns aoc.file-util
  (:require [clojure.java.io :as io]
            [clojure.string :as str]))

(defn read-file
  "Return full file contents from `path`."
  [path]
  (-> path io/resource slurp str/trim-newline))

(defn read-lines
  "Return file contents as collection of rows."
  [path]
  (-> path read-file str/split-lines))

(defn read-values
  "Return sequence of numeric values from input file which has numbers on each row.
  Supports negative signs."
  [path]
  (->> path read-lines (map #(Integer/parseInt %))))

(defn read-tsv
  "Return a list of rows, with each row as a list of values from the TSV."
  [path]
  (->> path read-lines (map #(str/split %1 #"\t"))))

(defn explode-digits
  "Turn input string of digits into sequence of numbers they represent."
  [s]
  (map #(Character/getNumericValue %) s))

(def alphagram (comp sort str/lower-case))

(defn anagram? [word candidate]
  (and (not= (str/lower-case word) (str/lower-case candidate))
       (= (alphagram word) (alphagram candidate))))
