(ns aoc.2015.d07-test
  (:require [aoc.2015.d07 :as sut]
            [clojure.test :refer :all]))

(deftest challenge
  (is (= 16076 (sut/part-1 sut/input)))
  (is (= 2797 (sut/part-2 sut/input))))
