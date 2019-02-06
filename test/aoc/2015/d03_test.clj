(ns aoc.2015.d03-test
  (:require [aoc.2015.d03 :as sut]
            [clojure.test :refer :all]))

(deftest part-1-examples
  (are [output input] (= output (sut/part-1 input))
    2 ">"
    4 "^>v<"
    2 "^v^v^v^v^v"))

(deftest part-2-examples
  (are [output input] (= output (sut/part-2 input))
    3 "^v"
    3 "^>v<"
    11 "^v^v^v^v^v"))

(deftest challenge
  (is (= 2592 (sut/part-1 sut/input)))
  (is (= 2360 (sut/part-2 sut/input))))
