(ns aoc.2020.d03-test
  (:require [aoc.2020.d03 :as sut]
            [clojure.test :refer :all]))

(deftest challenges
  (is (= 272 (sut/part-1 sut/input)))
  (is (= 3898725600 (sut/part-2 sut/input))))
