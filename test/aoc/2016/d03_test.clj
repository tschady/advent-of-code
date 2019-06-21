(ns aoc.2016.d03-test
  (:require [aoc.2016.d03 :as sut]
            [clojure.test :refer :all]))

(deftest challenge
  (is (= 1032 (sut/part-1 sut/input)))
  (is (= 1838 (sut/part-2 sut/input))))
