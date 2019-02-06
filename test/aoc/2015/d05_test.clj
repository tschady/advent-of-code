(ns aoc.2015.d05-test
  (:require [aoc.2015.d05 :as sut]
            [clojure.test :refer :all]))

(deftest part-1-examples
  (are [output input] (= output (sut/nice? input))
    true "ugknbfddgicrmopn"
    true "aaa"
    false "jchzalrnumimnmhp"
    false "haegwjzuvuyypxyu"
    false "dvszwmarrgswjxmb"))

(deftest part-2-examples
  (are [output input] (= output (sut/nice2? input))
    true "qjhvhtzxzqqjkmpb"
    true "xxyxx"
    false "uurcxstgmygtbstg"
    false "ieodomkazucvgmuy"))

(deftest challenge
  (is (= 255 (sut/part-1 sut/input)))
  (is (= 55 (sut/part-2 sut/input))))
