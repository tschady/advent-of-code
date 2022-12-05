(ns aoc.2022.d05-test
    (:require
     [aoc.2022.d05 :as sut]
     [clojure.test :refer :all]))

(deftest challenges
  (is (= "RTGWZTHLD" (sut/part-1 sut/input)))
  (is (= "STHGRZZFR" (sut/part-2 sut/input))))
