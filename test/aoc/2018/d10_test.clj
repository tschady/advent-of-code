(ns aoc.2018.d10-test
  (:require [aoc.2018.d10 :as sut]
            [clojure.test :refer :all]))

(deftest part1-examples
  (are [output input] (= output (sut/part-1 input))
    32     "9 players; last marble is worth 25 points"
    8317   "10 players; last marble is worth 1618 points"
    146373 "13 players; last marble is worth 7999 points"
    2764   "17 players; last marble is worth 1104 points"
    54718  "21 players; last marble is worth 6111 points"
    37305  "30 players; last marble is worth 5807 points"))

(deftest challenge
  (is (= 388131 (sut/part-1 sut/input)))
  (is (= 3239376988 (sut/part-2 sut/input))))
