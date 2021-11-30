(ns aoc.2017.d10-test
  (:require [aoc.2017.d10 :as sut]
            [clojure.test :refer :all]))

(deftest challenges
  (is (= 9656 (sut/part-1 sut/input)))
  (is (= "20b7b54c92bf73cf3e5631458a715149" (sut/part-2 sut/input))))
