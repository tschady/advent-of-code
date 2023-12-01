(ns aoc.2023.d01-test
    (:require
     [aoc.2023.d01 :as sut]
     [clojure.test :refer :all]))

(deftest challenges
  (is (= 54877 (sut/part-1 sut/input)))
  (is (= 54100 (sut/part-2 sut/input))))
