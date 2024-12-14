(ns aoc.2024.d14-test
    (:require
     [aoc.2024.d14 :as sut]
     [clojure.test :refer :all]))

(deftest challenges
  (is (= 224554908 (sut/part-1 [101 103] sut/input)))
  (is (= 6644 (sut/part-2 [101 103] sut/input))))
