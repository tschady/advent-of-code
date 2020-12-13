(ns aoc.math-util
  (:require [clojure.string :as str]))

(defn factors
  "Return vector of all factors of a given integer `n`"
  [n]
  (->> (range 1 (inc (Math/sqrt n)))
       (filter #(zero? (rem n %)))
       (mapcat #(vector % (/ n %)))
       (into (sorted-set))))

(defn series-sum
  "Returns the sum of all digits in this contiguous integer series,
  from `start` to `end`. Start defaults to 1."
  ([end] (/ (* end (inc end)) 2))
  ([start end] (- (series-sum end) (series-sum (dec start)))))

(defn vector-math
  "Return a result vector from mapping `op` across collection of N-dimensional input vectors.
  E.g. `(vector-math + [[1 1] [2 4]]) => [3 5]`"
  [op coll]
  (apply (partial mapv op) coll))

(defn digits->num
  "Given a sequence of digits, join them together into one integer."
  [xs]
  (->> xs (map str) str/join read-string))

(defn xor
  "Returns true if exclusive or satisfied over all inputs, else false."
  ([a b] (and (or a b)
              (not (and a b))))
  ([a b & more] (reduce xor (xor a b) more)))

(defn extended-gcd
  "The extended Euclidean algorithm--using Clojure code from RosettaCode for Extended Eucliean
  (see http://en.wikipedia.orwiki/Extended_Euclidean_algorithm)
  Returns a list containing the GCD and the Bézout coefficients
  corresponding to the inputs with the result: gcd followed by bezout coefficients "
  [a b]
  (cond (zero? a) [(Math/abs b) 0 1]
        (zero? b) [(Math/abs a) 1 0]
        :else (loop [s 0, s0 1
                     t 1, t0 0
                     r (Math/abs b)
                     r0 (Math/abs a)]
                (if (zero? r)
                  [r0 s0 t0]
                  (let [q (quot r0 r)]
                    (recur (- s0 (* q s)) s
                           (- t0 (* q t)) t
                           (- r0 (* q r)) r))))))

(defn mul-mod-inv
  "Get multiplicative modular inverse using extended Euclid's formula.
    gcd followed by Bezout coefficients. We want the 1st coefficients
   (i.e. second of extend-gcd result).  We compute mod base so result
    is between 0..(base-1).
  Return `nil` if `a` and `b` are not co-prime."
  [a b]
  (let [b (if (neg? b) (- b) b)
        a (if (neg? a) (- b (mod (- a) b)) a)
        egcd (extended-gcd a b)]
      (when (= (first egcd) 1); must be co-prime
        (mod (second egcd) b))))

(defn gcd
  "Return the Greatest Common Denominator of integers `a` and `b` using Euclid's formula."
  [a b]
  (first (extended-gcd a b)))

(defn extended-euclid
  ""
  [a b]
  (cond (zero? a) [b 0 1]
        (zero? b) [a 1 0])
  )


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

(defn count-on-bits
  "Return the number of '1' bits in the binary representation of integer `n`"
  [n]
  ;; Use Brian Kernighan's formula
  (loop [n n, c 0]
    (if (zero? n)
      c
      (recur (bit-and n (dec n)) (inc c)))))
