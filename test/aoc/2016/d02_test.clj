(ns aoc.2016.d02-test
  (:require [aoc.2016.d02 :as sut]
            [clojure.test :refer :all]))

(def test-input ["ULL"
                 "RRDDD"
                 "LURDL"
                 "UUUUD"])

(deftest part1-examples
  (is (= "1985" (sut/part-1 test-input))))

(deftest part2-examples
  (is (= "5DB3" (sut/part-2 test-input))))

(deftest challenge
  (is (= "98575" (sut/part-1 sut/input)))
  (is (= "CD8D4" (sut/part-2 sut/input))))
