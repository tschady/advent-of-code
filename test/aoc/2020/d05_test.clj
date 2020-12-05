(ns aoc.2020.d05-test
  (:require [aoc.2020.d05 :as sut]
            [clojure.test :refer :all]))

(deftest challenges
  (is (= 850 (sut/part-1 sut/input)))
  (is (= 599 (sut/part-2 sut/input))))
