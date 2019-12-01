(ns aoc.2019.d01-test
  (:require [aoc.2019.d01 :as sut]
            [clojure.test :refer :all]))

(deftest part1-examples
  (are [input output] (= output (sut/mass->fuel input))
    12 2
    14 2
    1969 654
    100756 33583))

(deftest part2-examples
  (are [input output] (= output (sut/total-fuel input))
    14 2
    1969 966
    100756 50346))

(deftest challenges
  (is (= 3150224 (sut/part-1 sut/input)))
  (is (= 4722484 (sut/part-2 sut/input))))
