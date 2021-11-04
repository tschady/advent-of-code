(ns aoc.2017.d15-test
  (:require [aoc.2017.d15 :as sut]
            [clojure.test :refer :all]))

(deftest challenges
  (is (= 609 (sut/part-1 sut/input)))
  (is (= 253 (sut/part-2 sut/input))))
