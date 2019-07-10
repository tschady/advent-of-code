(ns aoc.2015.d10-test
  (:require [aoc.2015.d10 :as sut]
            [clojure.test :refer :all]))

(deftest look-and-say-examples
  (are [output input] (= output (sut/look-and-say input))
    "11" "1"
    "21" "11"
    "1211" "21"
    "312211" "111221"))

(deftest challenge
  (is (= 329356 (sut/part-1 sut/input)))
  (is (= 4666278 (sut/part-2 sut/input))))
