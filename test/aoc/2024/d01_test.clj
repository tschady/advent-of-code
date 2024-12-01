(ns aoc.2024.d01-test
    (:require
     [aoc.2024.d01 :as sut]
     [clojure.test :refer :all]))

(deftest challenges
  (is (= 2176849 (sut/part-1 sut/input)))
  (is (= 23384288 (sut/part-2 sut/input))))
