(ns aoc.2024.d13
  (:require
   [aoc.file-util :as f]))

(def input (partition 3 (partition 2 (f/read-ints "2024/d13.txt"))))

(defn pulls [[[x1 y1] [x2 y2] [x y]]]
  (let [a (/ (- (* y x2) (* x y2)) (- (* x2 y1) (* x1 y2)))
        b (/ (- x (* a x1)) x2)]
    [a b]))

(defn valid?
  ([[a b]] (and (integer? a) (integer? b)))
  ([lim [a b]] (and (<= a lim) (<= b lim) (valid? [a b]))))

(defn cost [[a b]] (+ b (* 3 a)))

(defn embiggen [[c1 c2 [x y]]] [c1 c2 [(+ x 10000000000000) (+ y 10000000000000)]])

(defn solve [valid-fn? input] (reduce + (map cost (filter valid-fn? (map pulls input)))))

(defn part-1 [input] (solve (partial valid? 100) input))

(defn part-2 [input] (solve valid? (map embiggen input)))
