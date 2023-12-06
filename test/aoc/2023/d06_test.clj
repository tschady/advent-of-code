(ns aoc.2023.d06-test
    (:require
     [aoc.2023.d06 :as sut]
     [clojure.test :refer :all]))

(deftest challenges
  (is (= 2612736 (sut/part-1 sut/input)))
  (is (= 29891250 (sut/part-2 sut/input))))
