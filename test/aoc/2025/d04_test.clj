(ns aoc.2025.d04-test
    (:require
     [aoc.2025.d04 :as sut]
     [clojure.test :refer :all]))

(deftest challenges
  (is (= 1516 (sut/part-1 sut/input)))
  (is (= 9122 (sut/part-2 sut/input))))
