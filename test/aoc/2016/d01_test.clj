(ns aoc.2016.d01-test
  (:require [aoc.2016.d01 :as sut]
            [clojure.test :refer :all]))

(deftest part1-examples
  (are [output input] (= output (sut/part-1 input))
    5 "R2, L3"
    2 "R2, R2, R2"
    12 "R5, L5, R5, R3"))

(deftest challenge
  (is (= 230 (sut/part-1 sut/input)))
;  (is (= false (sut/part-2 sut/input)))
  )
