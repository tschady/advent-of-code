(ns aoc.2021.d17-test
  (:require [aoc.2021.d17 :as sut]
            [clojure.test :refer :all]))

(deftest dx-range
  (are [t vs] (= vs (sut/vel-range sut/vx [20 30] t))
    1 [20 31]
    2 [11 16]
    3 [8 12]
    4 [7 10]
    5 [6 9]
    6 [6 8]
    7 [6 8]
    100 [6 8]))

(def ex-input [20 30 -10 -5])

(deftest examples
  (is (= 45 (sut/part-1 ex-input)))
  (is (= 112 (sut/part-2 ex-input))))

(deftest challenges
  (is (= 30628 (sut/part-1 sut/input)))
  (is (= 4433 (sut/part-2 sut/input))))
