(ns aoc.2017.d08-test
  (:require [aoc.2017.d08 :as sut]
            [clojure.test :refer :all]))

(deftest challenges
  (is (= 4877 (sut/part-1 sut/input)))
  (is (= 5471 (sut/part-2 sut/input))))
