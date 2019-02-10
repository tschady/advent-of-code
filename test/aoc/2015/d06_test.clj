(ns aoc.2015.d06-test
  (:require [aoc.2015.d06 :as sut]
            [clojure.test :refer :all]))

(deftest part-1-examples
  (are [output input] (= output (sut/part-1 input))
    1000000 ["turn on 0,0 through 999,999"]
    1000 ["toggle 0,0 through 999,0"]
    4 ["turn on 499,499 through 500,500"]))

(deftest part-2-examples
  (are [output input] (= output (sut/part-2 input))
    1 ["turn on 0,0 though 0,0"]
    2000000 ["toggle 0,0 through 999,999"]))

(deftest challenge
  (is (= 543903 (sut/part-1 sut/input)))
  (is (= 14687245 (sut/part-2 sut/input))))
