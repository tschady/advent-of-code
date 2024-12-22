(ns aoc.2024.d22
  (:require
   [aoc.coll-util :as c]
   [aoc.file-util :as f]))

(def input (f/read-ints "2024/d22.txt"))

(defn mix [a b] (bit-xor a b))

(defn prune [n] (mod n 16777216))

(defn price [n] (mod n 10))

(defn evolve [n]
  (let [n1 (-> n  (bit-shift-left 6)  (mix n)  prune)
        n2 (-> n1 (bit-shift-right 5) (mix n1) prune)
        n3 (-> n2 (bit-shift-left 11) (mix n2) prune)]
    n3))

(defn part-1 [input]
  (reduce + (map #(nth (iterate evolve %) 2000) input)))

(defn delta->price [cnt n]
  (let [prices (map price (take cnt (iterate evolve n)))]
    ;; zip up the reverses so we overwrite later matches with earlier ones
    (zipmap (reverse (partition 4 1 (c/intervals prices))) (reverse prices))))

(defn part-2 [input]
  (->> input
       (map (partial delta->price 2000))
       (apply merge-with +)
       vals
       (apply max)))
