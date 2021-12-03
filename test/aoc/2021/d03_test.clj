(ns aoc.2021.d03-test
  (:require [aoc.2021.d03 :as sut]
            [clojure.test :refer :all]))

(def example ["00100"
              "11110"
              "10110"
              "10111"
              "10101"
              "01111"
              "00111"
              "11100"
              "10000"
              "11001"
              "00010"
              "01010"])

(deftest examples
  (is (= 198 (sut/part-1 example)))
  (is (= 230 (sut/part-2 example))))

(deftest challenges
  (is (= 3885894 (sut/part-1 sut/input)))
  (is (= 4375225 (sut/part-2 sut/input))))
