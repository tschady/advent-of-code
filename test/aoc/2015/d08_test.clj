(ns aoc.2015.d08-test
  (:require [aoc.2015.d08 :as sut]
            [clojure.test :refer :all]))

(deftest encoding-diff
  (are [output input] (= output (sut/decode-count input))
    0 "\"\""
    3 "\"abc\""
    7 "\"aaa\\\"aaa\""
    1 "\"\\x27\""))

(deftest part-2-examples
  (are [output input] (= output (sut/recode-count input))
    6 "\"\""
    9 "\"abc\""
    16 "\"aaa\\\"aaa\""
    11 "\"\\x27\""))

(deftest challenge
  (is (= 1342 (sut/part-1 sut/input)))
  (is (= 2074 (sut/part-2 sut/input))))
