(ns aoc.2017.d01
  (:require [aoc.util :as util]))

(def input (util/read-file "2017/d01.txt"))

(defn- solve-captcha
  "Returns the captcha 'checksum' of a given collection of paired 2-tuple
  numbers represented as characters.  Result is the sum of the values of
  each pairing that are equivalent."
  [pairs]
  (->> pairs
       (filter (partial apply =))
       (map first)
       (map #(Character/getNumericValue %))
       (reduce + 0)))

(defn part-1
  "Solve for captcha where pairs are adjacent, with the last and first char paired
  from the given input string `s`."
  [s]
  (let [pairings (partition 2 1 [(first s)] s)]
    (solve-captcha pairings)))

(defn part-2
  "Solve for captcha where pairs are 'halfway' around the circle of numbers
  from the given input string `s`, which must be of even length."
  [s]
  {:pre [(zero? (rem (count s) 2))]} ; problem asserts even length input
  (let [mid-point (/ (count s) 2)
        pairings (->> (seq s)
                      (split-at mid-point)
                      (apply map list))]
    ;; the circular list pairings are symmetrical, just multiply by 2
    (* 2 (solve-captcha pairings))))

(defn solve []
  [(part-1 input)
   (part-2 input)])
