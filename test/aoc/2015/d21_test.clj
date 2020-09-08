(ns aoc.2015.d21-test
  (:require [aoc.2015.d21 :as sut]
            [clojure.test :refer :all]))

(deftest challenge
  (is (= 121 (sut/part-1 sut/input sut/hero-hp)))
  (is (= 201 (sut/part-2 sut/input sut/hero-hp))))
