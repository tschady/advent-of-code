(ns aoc.2016.d16-test
  (:require [aoc.2016.d16 :as sut]
            [clojure.test :refer :all]))

(deftest dragon-grow
  (are [input output] (= output (sut/dragon-grow input))
    "1" "100"
    "0" "001"
    "111100001010" "1111000010100101011110000"))

(deftest fill-disk
  (is (= (seq "10000011110010000111") (sut/fill-disk 20 "10000"))))

(deftest challenges
  (is (= "10010010110011010" (sut/solve sut/disk-p1 sut/input)))
  (is (= "01010100101011100" (sut/solve sut/disk-p2 sut/input))))
