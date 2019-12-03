(ns aoc.file-util
  (:require [clojure.data.csv :as csv]
            [clojure.java.io :as io]
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

(defn read-csv
  "Return a list of rows, with each row as a list of values from the TSV."
  [path]
  (->> path read-file csv/read-csv))


