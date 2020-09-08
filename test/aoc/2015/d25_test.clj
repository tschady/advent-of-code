(ns aoc.2015.d25-test
  (:require [aoc.2015.d25 :as sut]
            [clojure.test :refer :all]))

(deftest challenge
  (is (= 8997277 (sut/part-1 sut/input))))
