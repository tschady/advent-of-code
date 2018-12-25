(ns aoc.2018.d11-test
  (:require [aoc.2018.d11 :as sut]
            [clojure.test :refer :all]))

(deftest cell-power-examples
  (are [output cell grid] (= output (sut/cell-power grid cell))
    4 [3 5] 8
    -5 [122 79] 57
    0 [217 196] 39
    4 [101 153] 71))

(deftest part1-examples
  (are [output input] (= output (sut/part-1 input))
    "33,45" 18
    "21,61" 42))

(deftest part2-examples
  (are [output input] (= output (sut/part-2 input))
    "90,269,16" 18
    "232,251,12" 42))

(deftest challenge
  (is (= "245,14" (sut/part-1 sut/input)))
  (is (= "235,206,13" (sut/part-2 sut/input))))
