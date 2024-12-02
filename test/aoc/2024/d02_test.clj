(ns aoc.2024.d02-test
    (:require
     [aoc.2024.d02 :as sut]
     [clojure.test :refer :all]))

(deftest challenges
  (is (= 407 (sut/part-1 sut/input)))
  (is (= 459 (sut/part-2 sut/input))))
