(ns aoc.2017.d06-test
  (:require [aoc.2017.d06 :as sut]
            [clojure.test :refer :all]))

(deftest part-1-examples
  (are [input output] (= output (sut/realloc-blocks input))
    [0 2 7 0] [2 4 1 2]
    [2 4 1 2] [3 1 2 3]
    [3 1 2 3] [0 2 3 4]
    [0 2 3 4] [1 3 4 1]
    [1 3 4 1] [2 4 1 2]))

(deftest part-1
  (is (= 5 (sut/part-1 [0 2 7 0]))))

(deftest part-2
  (is (= 4 (sut/part-2 [0 2 7 0]))))

(deftest challenge
  (is (= 5042 (sut/part-1 sut/input)))
  (is (= 1086 (sut/part-2 sut/input))))
