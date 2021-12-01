(ns aoc.2021.d01-test
  (:require [aoc.2021.d01 :as sut]
            [clojure.test :refer :all]))

(deftest challenges
  (is (= 1527 (sut/part-1 sut/input)))
  (is (= 1575 (sut/part-2 sut/input))))
