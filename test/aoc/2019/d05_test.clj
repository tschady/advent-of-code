(ns aoc.2019.d05-test
  (:require [aoc.2019.d05 :as sut]
            [clojure.test :refer :all]))

(def test-input2  [3,21,1008,21,8,20,1005,20,22,107,8,21,20,1006,20,31,
                   1106,0,36,98,0,0,1002,21,125,20,4,20,1105,1,46,104,
                   999,1105,1,46,1101,1000,1,20,4,20,1105,1,46,98,99])

(deftest part-2-examples
  (is (= 999 (sut/solve test-input2 [7])))
  (is (= 1000 (sut/solve test-input2 [8])))
  (is (= 1001 (sut/solve test-input2 [9]))))

(deftest challenges
  (is (= 9654885 (sut/part-1 sut/input)))
  (is (= 7079459 (sut/part-2 sut/input))))

;; part 2 7079459
