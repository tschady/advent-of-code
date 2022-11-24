(ns aoc.2016.d21-test
  (:require [aoc.2016.d21 :as sut]
            [clojure.test :refer :all]))

(def example-1
  ["swap position 4 with position 0"
   "swap letter d with letter b"
   "reverse positions 0 through 4"
   "rotate left 1 step"
   "move position 1 to position 4"
   "move position 3 to position 0"
   "rotate based on position of letter b"
   "rotate based on position of letter d"])

(deftest example
  (is (= "decab" (sut/part-1 "abcde" example-1))))

(deftest challenges
  (is (= "dgfaehcb" (sut/part-1 "abcdefgh" sut/input)))
  (is (= "fdhgacbe" (sut/part-2 "fbgdceah" sut/input))))
