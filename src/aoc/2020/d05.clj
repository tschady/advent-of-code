(ns aoc.2020.d05
  (:require
   [aoc.file-util :as file-util]
   [aoc.math-util :as math-util]
   [clojure.string :as str]))

(def input (file-util/read-lines "2020/d05.txt"))

(defn seat-id [seat-code]
  (-> seat-code
      (str/escape {\B 1, \F 0, \R 1, \L 0})
      (Integer/parseInt 2)))

(defn part-1 [input] (apply max (map seat-id input)))

(defn part-2 [input]
  (let [seat-ids (map seat-id input)
        [start end] ((juxt (partial apply min) (partial apply max)) seat-ids)
        expected-sum (math-util/series-sum start end)
        sum (reduce + seat-ids)]
    (- expected-sum sum)))
