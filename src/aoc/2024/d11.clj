(ns aoc.2024.d11
  (:require
   [aoc.file-util :as f]))

(def input (f/read-ints "2024/d11.txt"))

(defn digit-count [n] (-> n Math/log10 int inc))

(defn split [n]
  (let [div (int (Math/pow 10 (quot (digit-count n) 2)))]
    (list (quot n div) (rem n div))))

(def step*
  (memoize
   (fn [t n]
     (cond
       (zero? t)               1
       (zero? n)               (step* (dec t) 1)
       (even? (digit-count n)) (reduce + (map #(step* (dec t) %) (split n)))
       :else                   (step* (dec t) (* 2024 n))))))

(defn solve [input t] (transduce (map #(step* t %)) + input))

(defn part-1 [input] (solve input 25))

(defn part-2 [input] (solve input 75))
