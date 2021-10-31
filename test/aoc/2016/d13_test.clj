(ns aoc.2016.d13-test
  (:require [aoc.2016.d13 :as sut]
            [clojure.test :refer :all]))

(deftest examples
  (is (= 11 (sut/part-1 10 [1 1] [7 4] 20))))

(deftest challenges
  (is (= 92 (sut/part-1 sut/input [1 1] [31 39] 50)))
  (is (= 124 (sut/part-2 sut/input [1 1] 50))))
