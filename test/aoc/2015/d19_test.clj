(ns aoc.2015.d19-test
  (:require [aoc.2015.d19 :as sut]
            [clojure.test :refer :all]))

(def test-rules1 ["H => HO"
                 "H => OH"
                 "O => HH"])

(def test-rules2 ["e => H"
                  "e => O"
                  "H => HO"
                  "H => OH"
                  "O => HH"])

(deftest part-1-examples
  (are [input output] (= output (sut/part-1 input (sut/parse-rules test-rules1)))
    "HOH" 4
    "HOHOHO" 7))

(deftest part-2-examples
  (are [input output] (= output (sut/part-2 input (sut/parse-rules test-rules2)))
    "HOH" 3
    "HOHOHO" 6))

(deftest challenge
  (is (= 535 (sut/part-1 sut/molecule sut/rules)))
  (is (= 212 (sut/part-2 sut/molecule sut/rules))))
