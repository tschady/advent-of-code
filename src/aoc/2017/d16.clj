(ns aoc.2017.d16
  (:require
   [aoc.coll-util :refer [first-duplicate x-nth]]
   [aoc.file-util :as f]
   [aoc.string-util :as s]))

(def input (first (f/read-csv "2017/d16.txt")))

(defn move [dancers cmd]
  (case (first cmd)
    \s (s/rotate-right dancers (first (s/ints cmd)))
    \x (apply s/swap-pos dancers (s/ints cmd))
    \p (apply s/swap-letter dancers (mapcat seq (rest (re-find #"(.)/(.)" cmd))))))

(defn infinite-dances [start input]
  (iterate #(reduce move % input) (seq start)))

(defn part-1 [start input]
  (apply str (x-nth 1 (infinite-dances start input))))

(defn part-2 [start input]
  (let [[_ [d0 d1]] (first-duplicate (infinite-dances start input))
        steps (mod 1000000000 (- d1 d0))]
    (apply str (x-nth steps (infinite-dances start input)))))
