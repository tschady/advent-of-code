(ns aoc.2018.d07-test
  (:require [aoc.2018.d07 :as sut]
            [clojure.test :refer :all]))

(def test-input
  ["Step C must be finished before step A can begin."
   "Step C must be finished before step F can begin."
   "Step A must be finished before step B can begin."
   "Step A must be finished before step D can begin."
   "Step B must be finished before step E can begin."
   "Step D must be finished before step E can begin."
   "Step F must be finished before step E can begin."])

(deftest part1-examples
  (is (= "CABDFE" (sut/part-1 test-input))))

(deftest part2-examples
  (is (= 15 (sut/time-to-build (sut/build-graph test-input) 2 0))))

(deftest challenge
  (is (= "GJFMDHNBCIVTUWEQYALSPXZORK" (sut/part-1 sut/input)))
  (is (= 1050 (sut/part-2 sut/input))))
