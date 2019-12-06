(ns aoc.2019.d06-test
  (:require [aoc.2019.d06 :as sut]
            [clojure.test :refer :all]))

(def test-input1
  ["COM)B" "B)C" "C)D" "D)E" "E)F" "B)G" "G)H" "D)I" "E)J" "J)K" "K)L"])

(def test-input2 (conj test-input1 "K)YOU" "I)SAN"))

(deftest part1-example
  (is (= 42 (sut/part-1 test-input1))))

(deftest part2-example
  (is (= 4 (sut/part-2 test-input2))))

(deftest challenges
  (is (= 119831 (sut/part-1 sut/input)))
  (is (= 322 (sut/part-2 sut/input))))
