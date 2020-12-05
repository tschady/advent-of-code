(ns aoc.2020.d05
  (:require [aoc.file-util :as file-util]
            [clojure.string :as str]))

(def input (file-util/read-lines "2020/d05.txt"))

(defn seat-id [seat-code]
  (-> seat-code
      (str/escape {\B 1, \F 0, \R 1, \L 0})
      (Integer/parseInt 2)))

(defn part-1 [input] (apply max (map seat-id input)))

(defn part-2 [input]
  (let [taken-seats (sort (map seat-id input))
        all-seats (range (first taken-seats) (inc (last taken-seats)))]
    (first (clojure.set/difference (set all-seats) (set taken-seats)))))
