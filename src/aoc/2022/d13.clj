(ns aoc.2022.d13
  (:require
   [aoc.file-util :as f]
   [clojure.string :as str]))

(def input (f/read-file "2022/d13.txt"))

(defn parse-packets [input] (read-string (str "[" input "]")))

(defn compare-packets [L R]
  (cond
    (and (sequential? L) (number? R))     (compare-packets L (list R))
    (and (number? L) (sequential? R))     (compare-packets (list L) R)
    (and (sequential? L) (sequential? R)) (let [c (compare-packets (first L) (first R))]
                                            (if (zero? c)
                                              (compare-packets (next L) (next R))
                                              c))
    :else (compare L R)))

(defn part-1 [input]
  (->> (parse-packets input)
       (partition 2)
       (keep-indexed #(when (neg? (apply compare-packets %2)) (inc %1)))
       (reduce +)))

(defn part-2 [input]
  (let [dividers #{[[2]] [[6]]}
        ordered (->> (parse-packets input)
                     (concat dividers)
                     (sort compare-packets))]
    (->> dividers
         (map #(inc (.indexOf ordered %)))
         (reduce *))))
