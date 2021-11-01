(ns aoc.2016.d08-test
  (:require [aoc.2016.d08 :as sut]
            [clojure.test :refer :all]))

(deftest challenges
  (is (= 110 (sut/part-1 sut/input))))
