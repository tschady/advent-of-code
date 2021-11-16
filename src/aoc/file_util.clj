(ns aoc.file-util
  (:require
   [aoc.string-util :as string-util]
   [clojure.data.csv :as csv]
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

(defn read-chunks
  "Return file contents as collection of chunks, where chunks are separated by a
  full blank line."
  [path]
  (-> path read-file (str/split #"\n\n")))

(defn read-ints
  "Return vector of integers from input, supports negative signs."
  [path]
  (vec (string-util/ints (read-file path))))

(defn read-tsv
  "Return a list of rows, with each row as a list of values from the TSV."
  [path]
  (->> path read-lines (map #(str/split %1 #"\t"))))

(defn read-csv
  "Return a list of rows, with each row as a list of values from the TSV."
  [path]
  (->> path read-file csv/read-csv))


