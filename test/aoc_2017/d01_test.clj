(ns aoc-2017.d01-test
  (:require [aoc-2017.d01 :as sut]
            [clojure.test :refer :all]))

(deftest part-1-examples
  (are [input output] (= output (sut/part-1 input))
    "1122" 3
    "1111" 4
    "1234" 0
    "91212129" 9))

(deftest part-2-examples
  (are [input output] (= output (sut/part-2 input))
    "1212" 6
    "1221" 0
    "123425" 4
    "123123" 12
    "12131415" 4))

(deftest challenge
  (is (= 1341 (sut/part-1 sut/input)))
  (is (= 1348 (sut/part-2 sut/input))))
