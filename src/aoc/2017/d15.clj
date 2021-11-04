(ns aoc.2017.d15
  (:require [aoc.file-util :as file-util]))

(def input (->> (file-util/read-lines "2017/d15.txt")
                (map #(re-find #"(\d+)" %))
                (map first)
                (map #(Long/parseLong %))))

(defn next-A [n] (rem (* n 16807) 2147483647))

(defn next-B [n] (rem (* n 48271) 2147483647))

(defn last-16 [n] (bit-and 65535 n))

(defn part-1 [input]
  (let [A-xs (take 40000000 (iterate next-A (first input)))
        B-xs (take 40000000 (iterate next-B (second input)))]
    (count (filter true? (map #(= (last-16 %) (last-16 %2)) A-xs B-xs)))))

(defn part-2 [input]
  (let [A-xs (->> (first input)
                  (iterate next-A)
                  (filter #(zero? (mod % 4)))
                  (take 5000000))
        B-xs (->> (second input)
                  (iterate next-B)
                  (filter #(zero? (mod % 8)))
                  (take 5000000))]
    (count (filter true? (map #(= (last-16 %) (last-16 %2)) A-xs B-xs)))))
