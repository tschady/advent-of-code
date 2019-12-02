(ns aoc.2019.d02-test
  (:require [aoc.2019.d02 :as sut]
            [clojure.test :refer :all]))

(deftest part1-examples
  (are [input output] (= output (sut/run-tape input))
    [1,0,0,0,99] [2,0,0,0,99]
    [2,3,0,3,99] [2,3,0,6,99]
    [2,4,4,5,99,0] [2,4,4,5,99,9801]
    [1,1,1,4,99,5,6,0,99] [30,1,1,4,2,5,6,0,99]))

(deftest challenges
  (is (= 4930687 (sut/part-1 sut/input)))
  (is (= 5335 (sut/part-2 sut/input))))
