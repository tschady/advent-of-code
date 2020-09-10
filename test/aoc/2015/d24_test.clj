(ns aoc.2015.d24-test
  (:require [aoc.2015.d24 :as sut]
            [clojure.test :refer :all]))

(deftest challenge
  (is (= 10439961859 (sut/part-1 sut/input)))
  (is (= 72050269 (sut/part-2 sut/input))))
