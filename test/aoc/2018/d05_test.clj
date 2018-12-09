(ns aoc.2018.d05-test
  (:require [aoc.2018.d05 :as sut]
            [clojure.test :refer :all]))

(def test-input "dabAcCaCBAcCcaDA")

(deftest alchemy
  (is (= "dabCBAcaDA" (apply str (sut/alchemize test-input)))))

(deftest part1-examples
  (is (= 10 (sut/part-1 test-input))))

(deftest part2-examples
  (is (= 4 (sut/part-2 test-input))))

(deftest challenge
  (is (= 11894 (sut/part-1 sut/input)))
  (is (= 5310 (sut/part-2 sut/input))))
