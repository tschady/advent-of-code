(ns aoc.2018.d23-test
  (:require [aoc.2018.d23 :as sut]
            [clojure.test :refer :all]))

(def test-input1 ["pos=<0,0,0>, r=4"
                  "pos=<1,0,0>, r=1"
                  "pos=<4,0,0>, r=3"
                  "pos=<0,2,0>, r=1"
                  "pos=<0,5,0>, r=3"
                  "pos=<0,0,3>, r=1"
                  "pos=<1,1,1>, r=1"
                  "pos=<1,1,2>, r=1"
                  "pos=<1,3,1>, r=1"])


(def test-input2 ["pos=<10,12,12>, r=2"
                  "pos=<12,14,12>, r=2"
                  "pos=<16,12,12>, r=4"
                  "pos=<14,14,14>, r=6"
                  "pos=<50,50,50>, r=200"
                  "pos=<10,10,10>, r=5"])

(deftest part1-examples
  (is (= 7 (sut/part-1 test-input1))))

#_(deftest part2-examples
  (is (= 36 (sut/part-2 test-input2))))

(deftest challenge
  (is (= 410 (sut/part-1 sut/input)))
  #_(is (= false (sut/part-2 sut/input))))
