(ns aoc.math-util
  (:require [clojure.string :as str]))

(defn factors
  "Return vector of all factors of a given integer `n`"
  [n]
  (->> (range 1 (inc (Math/sqrt n)))
       (filter #(zero? (rem n %)))
       (mapcat #(vector % (/ n %)))
       (into (sorted-set))))

(defn digits->num
  "Given a sequence of digits, join them together into one integer."
  [xs]
  (->> xs (map str) str/join read-string))

(defn xor
  "Returns true if exclusive or satisfied over all inputs, else false."
  ([a b] (and (or a b)
              (not (and a b))))
  ([a b & more] (reduce xor (xor a b) more)))

(defn change-combos
  "Return all possible combinations from the set of `denoms` that exactly
  sum to `amt`."
  ([amt denoms] (change-combos amt denoms [[]]))
  ([amt denoms acc]
   (cond (zero? amt) acc
         (or (neg? amt) (empty? denoms)) (pop acc)
         :else (concat (change-combos amt (rest denoms) acc)
                       (change-combos (- amt (first denoms))
                                      (rest denoms)
                                      (update acc (dec (count acc)) conj (first denoms)))))))

(defn change-permutation-count
  "Return the count of all possible permutations from the set of `denoms`
  that exactly sum to `amt`."
  [amt denoms]
  (cond (= amt 0) 1
        (or (< amt 0) (empty? denoms)) 0
        :else (+ (change-permutation-count amt (rest denoms))
                 (change-permutation-count (- amt (first denoms)) denoms))))
