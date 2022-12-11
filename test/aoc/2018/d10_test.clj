(ns aoc.2018.d10-test
    (:require
     [aoc.2018.d10 :as sut]
     [clojure.test :refer :all]))

(deftest challenges
  (is (= "HKJFAKAF" (sut/part-1 sut/input)))
  (is (= 10888 (sut/part-2 sut/input))))
