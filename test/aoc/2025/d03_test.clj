(ns aoc.2025.d03-test
    (:require
     [aoc.2025.d03 :as sut]
     [clojure.test :refer :all]))

(deftest challenges
  (is (= 17321 (sut/part-1 sut/input)))
  (is (= 171989894144198N (sut/part-2 sut/input))))
