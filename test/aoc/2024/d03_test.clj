(ns aoc.2024.d03-test
    (:require
     [aoc.2024.d03 :as sut]
     [clojure.test :refer :all]))

(deftest challenges
  (is (= 174960292 (sut/part-1 sut/input)))
  (is (= 56275602 (sut/part-2 sut/input))))
