(ns aoc.2015.d14-test
  (:require [aoc.2015.d14 :as sut]
            [clojure.test :refer :all]))

(def test-input
  ["Comet can fly 14 km/s for 10 seconds, but then must rest for 127 seconds."
   "Dancer can fly 16 km/s for 11 seconds, but then must rest for 162 seconds."])

(deftest part-1-examples
  (is (= 1120 (sut/part-1 test-input 1000))))

(deftest part-2-examples
  (is (= 689 (sut/part-2 test-input 1000))))

(deftest challenge
  (is (= 2660 (sut/part-1 sut/input 2503)))
  (is (= 1256 (sut/part-2 sut/input 2503))))
