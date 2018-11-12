(ns aoc.2017.d05-test
  (:require [aoc.2017.d05 :as sut]
            [clojure.test :refer :all]))

(deftest part-1-examples
  (are [output input] (= output (sut/part-1 input))
    5 [0 3 0 1 -3]))

(deftest part-1-examples
  (are [output input] (= output (sut/part-2 input))
    10 [0 3 0 1 -3]))

(deftest challenge
  (is (= 318883 (sut/part-1 sut/input)))
  (is (= 23948711 (sut/part-2 sut/input))))
