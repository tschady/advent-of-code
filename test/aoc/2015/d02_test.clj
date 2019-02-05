(ns aoc.2015.d02-test
  (:require [aoc.2015.d02 :as sut]
            [clojure.test :refer :all]))

(deftest part-1-examples
  (are [output input] (= output (sut/paper-needed input))
    58 [2 3 4]
    43 [1 1 10]))

(deftest part-2-examples
  (are [output input] (= output (sut/ribbon-needed input))
    34 [2 3 4]
    14 [1 1 10]))

(deftest challenge
  (is (= 1588178 (sut/part-1 sut/input)))
  (is (= 3783758 (sut/part-2 sut/input))))
