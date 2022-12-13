(ns aoc.2017.d13-test
    (:require
     [aoc.2017.d13 :as sut]
     [clojure.test :refer :all]))

(def ex [[0 3]
         [1 2]
         [4 4]
         [6 4]])

(deftest challenges
  (is (= 1504 (sut/part-1 sut/input)))
  (is (= 3823370 (sut/part-2 sut/input))))
