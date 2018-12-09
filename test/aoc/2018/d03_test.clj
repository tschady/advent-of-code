(ns aoc.2018.d03-test
  (:require [aoc.2018.d03 :as sut]
            [clojure.test :refer :all]))

(def test-claims ["#1 @ 1,3: 4x4"
                  "#2 @ 3,1: 4x4"
                  "#3 @ 5,5: 2x2"])

(deftest part1-examples
  (is (= 4 (sut/part-1 test-claims))))

(deftest part2-examples
  (is (= 3 (sut/part-2 test-claims))))

(deftest challenge
  (is (= 104712 (sut/part-1 sut/input)))
  (is (= 840 (sut/part-2 sut/input))))
