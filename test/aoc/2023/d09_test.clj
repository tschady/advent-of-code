(ns aoc.2023.d09-test
    (:require
     [aoc.2023.d09 :as sut]
     [clojure.test :refer :all]))

(def ex [[0 3 6 9 12 15]
         [1 3 6 10 15 21]
         [10 13 16 21 30 45]])

(deftest challenges
  (is (= 2008960228 (sut/part-1 sut/input)))
  (is (= 1097 (sut/part-2 sut/input))))
