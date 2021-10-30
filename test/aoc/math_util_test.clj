(ns aoc.math-util-test
  (:require [aoc.math-util :as sut]
            [clojure.test :refer :all]))

(deftest count-on-bits-examples
  (are [input output] (= output (sut/count-on-bits input))
    1 1
    2 1
    3 2
    7 3
    8 1
    12 2
    15 4))
