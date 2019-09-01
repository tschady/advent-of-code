(ns aoc.2015.d12-test
  (:require [aoc.2015.d12 :as sut]
            [clojure.test :refer :all]))

(deftest part-1-examples
  (are [output input] (= output (sut/part-1 input))
    6 "[1,2,3]"
    6 "{\"a\":2, \"b\":4}"
    3 "[[[3]]]"
    3 "{\"a\":{\"b\":4},\"c\":-1}"
    0 "{\"a\":[-1,1]}"
    0 "[-1,{\"a\":1}]"
    0 "[]"
    0 "{}"))

(deftest part-2-examples
  (are [output input] (= output (sut/part-2 input))
    6 "[1,2,3]"
    4 "[1,{\"c\":\"red\",\"b\":2},3]"
    0 "{\"d\":\"red\",\"e\":[1,2,3,4],\"f\":5}"
    6 "[1,\"red\",5]"))

(deftest challenge
  (is (= 119433 (sut/part-1 sut/input)))
  (is (= 68466 (sut/part-2 sut/input))))
