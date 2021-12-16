(ns aoc.2021.d16-test
  (:require [aoc.2021.d16 :as sut]
            [clojure.test :refer :all]))



(deftest challenges
  (is (= 996 (sut/part-1 sut/input)))
  (is (= 96257984154 (sut/part-2 sut/input))))
