(ns aoc.2022.d06-test
    (:require
     [aoc.2022.d06 :as sut]
     [clojure.test :refer :all]))

(deftest challenges
  (is (= 1100 (sut/part-1 sut/input)))
  (is (= 2421 (sut/part-2 sut/input))))
