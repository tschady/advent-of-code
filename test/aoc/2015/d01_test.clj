(ns aoc.2015.d01-test
  (:require [aoc.2015.d01 :as sut]
            [clojure.test :refer :all]))

(deftest part-1-examples
  (are [output input] (= output (sut/part-1 input))
    0 "(())"
    0 "()()"
    3 "(()(()("
    3 "((("
    3 "))((((("
    -1 "())"
    -1 "))("
    -3 ")))"
    -3 ")())())"))

(deftest part-2-examples
  (are [output input] (= output (sut/part-2 input))
    1 ")"
    5 "()())"))

(deftest challenge
  (is (= 138 (sut/part-1 sut/input)))
  (is (= 1771 (sut/part-2 sut/input))))
