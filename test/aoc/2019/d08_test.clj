(ns aoc.2019.d06-test
  (:require [aoc.2019.d06 :as sut]
            [clojure.test :refer :all]))

(def test-input2 "0222112222120000")

(deftest part1-example
  (is (= 42 (sut/part-1 test-input1))))

(deftest part2-example
  (is (= 4 (sut/part-2 test-input2))))

(deftest challenges
  (is (= 1920 (sut/part-1 sut/input)))
  (is (= "PCULA" (sut/part-2 sut/input))))
