(ns aoc.2016.d09-test
  (:require [aoc.2016.d09 :as sut]
            [clojure.test :refer :all]))

(deftest part1-examples
  (are [input output] (= output (sut/uncompress input))
    "ADVENT"            "ADVENT"
    "A(1x5)BC"          "ABBBBBC"
    "(3x3)XYZ"          "XYZXYZXYZ"
    "A(2x2)BCD(2x2)EFG" "ABCBCDEFEFG"
    "(6x1)(1x3)A"       "(1x3)A"
    "X(8x2)(3x3)ABCY"   "X(3x3)ABC(3x3)ABCY"))

#_(deftest part2-examples
  (is (true? (sut/ssl? "aba[bab]xyz")))
  (is (false? (sut/ssl? "xyx[xyx]xyx")))
  (is (true? (sut/ssl? "aaa[kek]eke")))
  (is (true? (sut/ssl? "zazbz[bzb]cdb"))))

(deftest challenge
  (is (= 120765 (sut/part-1 sut/input)))
  (is (= 11658395076 (sut/part-2 sut/input))))
