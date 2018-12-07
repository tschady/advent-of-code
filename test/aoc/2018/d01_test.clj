(ns aoc.2018.d01-test
  (:require [aoc.2018.d01 :as sut]
            [clojure.test :refer :all]))

(deftest part1-examples
  (are [input output] (= output (sut/part-1 input))
    [+1 +1 +1] 3
    [+1 +1 -2] 0
    [-1 -2 -3] -6))

(deftest part2-examples
  (are [input output] (= output (sut/part-2 input))
    [+1 -1] 0
    [+3 +3 +4 -2 -4] 10
    [-6 +3 +8 +5 -6] 5
    [+7 +7 -2 -7 -4] 14))

(deftest challenge
  (is (= 400 (sut/part-1 sut/input)))
  (is (= 232 (sut/part-2 sut/input))))
