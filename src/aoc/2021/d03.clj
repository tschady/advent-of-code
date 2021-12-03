(ns aoc.2021.d03
  (:require
   [aoc.file-util :as file-util]
   [aoc.string-util :refer [s->int]]))

(def input (file-util/read-lines "2021/d03.txt"))

(defn compare-col-freq
  [input compare-fn i]
  (let [fr (frequencies (map #(nth % i) input))]
    (if (compare-fn (get fr \1) (get fr \0)) \1 \0)))

(defn part-1 [input]
  (->> (range (count (first input)))
       (map (partial compare-col-freq input >=))
       ((juxt identity #(replace {\1 \0, \0 \1} %)))
       (map #(s->int % 2))
       (reduce *)))

(defn rating
  [op input]
  (-> (reduce (fn [nums i]
                (if (= 1 (count nums))
                  (reduced nums)
                  (filter #(= (compare-col-freq nums op i) (nth % i)) nums)))
              input
              (range (count (first input))))
      first
      (s->int 2)))

(defn part-2 [input]
  (* (rating >= input) (rating < input)))
