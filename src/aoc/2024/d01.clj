(ns aoc.2024.d01
    (:require
     [aoc.file-util :as f]
     [aoc.string-util :as s]))

(def input (f/read-ints "2024/d01.txt"))

(defn make-lists [xs] (apply map list (partition 2 xs)))

(defn part-1 [input]
  (->> (make-lists input)
       (map sort)
       (apply map (comp abs -))
       (reduce + 0)))

(defn part-2 [input]
  (let [[a b] (make-lists input)
        freq (frequencies b)]
    (reduce + 0 (map #(* % (get freq % 0)) a))))
