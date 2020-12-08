(ns aoc.2020.d08-test
  (:require [aoc.2020.d08 :as sut]
            [clojure.test :refer :all]))

(deftest challenges
  (is (= 1317 (sut/part-1 sut/input)))
  (is (= 1033 (sut/part-2 sut/input))))


