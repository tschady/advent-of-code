(ns aoc.grid-test
  (:require [aoc.grid :as sut]
            [clojure.test :refer :all]))

(deftest manhattan-dist
  (are [out a b] (= out (sut/manhattan-dist a b))
    0 [2 2] [2 2]
    1 [-1 -1] [-1 -2]
    1 [-1 -1] [-1 0]
    3 [3 -100] [6 -100]))
