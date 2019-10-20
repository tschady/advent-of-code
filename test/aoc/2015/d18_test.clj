(ns aoc.2015.d18-test
  (:require [aoc.2015.d18 :as sut]
            [clojure.test :refer :all]))

(deftest challenge
  (is (= 1061 (sut/part-1 sut/input)))
  (is (= 1006 (sut/part-2 sut/input))))
