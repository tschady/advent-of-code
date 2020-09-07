(ns aoc.2015.d20-test
  (:require [aoc.2015.d20 :as sut]
            [clojure.test :refer :all]))

(deftest challenge
  (is (= 831600 (sut/part-1 sut/input)))
  (is (= 884520 (sut/part-2 sut/input))))
