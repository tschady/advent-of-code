(ns aoc.2020.d10-test
  (:require [aoc.2020.d10 :as sut]
            [clojure.test :refer :all]))

(deftest challenges
  (is (= 2046 (sut/part-1 sut/input)))
  (is (= 1157018619904 (sut/part-2 sut/input))))
