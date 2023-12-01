(ns aoc.2023.d01
  (:require
   [aoc.file-util :as f]
   [clojure.string :as str]))

(def input (f/read-lines "2023/d01.txt"))

(def word2num {"one" "1" "two" "2" "three" "3" "four" "4" "five" "5"
               "six" "6" "seven" "7" "eight" "8" "nine" "9"})

(defn get-calibration
  "Returns the two-digit number comprised of the first and last digit
  appearing in `s` as determined by regex `re`"
  [re s]
  (->> (re-seq re s)
       ((juxt first last))
       (map second)
       (map #(get word2num % %))
       (apply str)
       parse-long))

(defn solve [input re]
  (reduce + 0 (map (partial get-calibration re) input)))

(defn part-1 [input]
  (solve input #"(\d)"))

(defn part-2 [input]
  (solve input (re-pattern (str "(?=(\\d|" (str/join "|" (keys word2num)) "))"))))
