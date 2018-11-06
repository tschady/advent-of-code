(ns aoc-2017.d03-test
  (:require [aoc-2017.d03 :as sut]
            [clojure.test :refer :all]))

(deftest part-1-examples
  (are [input output] (= output (sut/part-1 input))
    1 0
    12 3
    23 2
    1024 31))

(deftest challenge
  (is (= 371 (sut/part-1 sut/input))))
