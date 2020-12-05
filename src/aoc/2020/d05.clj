(ns aoc.2020.d05
  (:require [aoc.file-util :as file-util]))

(def input (file-util/read-lines "2020/d05.txt"))

(defn seat-id [seat-map]
  (let [binary-str (apply str (map {\B 1, \F 0, \R 1, \L 0} seat-map))]
    (Integer/parseInt binary-str 2)))

(defn part-1 [input] (apply max (map seat-id input)))

(defn part-2 [input]
  (let [full-seats (sort (map seat-id input))
        all-seats (range (first full-seats) (inc (last full-seats)))]
    (first (clojure.set/difference (set all-seats) (set full-seats)))))
