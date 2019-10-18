(ns aoc.2015.d16-test
  (:require [aoc.2015.d16 :as sut]
            [clojure.test :refer :all]))

(deftest challenge
  (is (= 213 (sut/part-1 sut/input)))
  (is (= 323 (sut/part-2 sut/input))))
