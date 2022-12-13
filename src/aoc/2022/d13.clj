(ns aoc.2022.d13
  (:require
   [aoc.file-util :as f]
   [clojure.string :as str]))

(def input (f/read-file "2022/d13.txt"))

(defn parse-packets [input] (read-string (str "[" input "]")))

(defn compare-packets [L R]
  (cond
    (and (nil? L) (nil? R))       0
    (nil? R)                      1
    (nil? L)                      -1
    (and (number? L) (number? R)) (compare L R)
    (number? R)                   (compare-packets L (list R))
    (number? L)                   (compare-packets (list L) R)

    (and (sequential? L) (sequential? R))
    (let [c (compare-packets (first L) (first R))]
      (if (zero? c)
        (compare-packets (next L) (next R))
        c))
    :else (throw (AssertionError. (str "Unhandled packets" L R)))))

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
