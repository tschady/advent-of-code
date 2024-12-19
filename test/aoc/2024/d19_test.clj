(ns aoc.2024.d19-test
    (:require
     [aoc.2024.d19 :as sut]
     [clojure.test :refer :all]))

(deftest challenges
  (is (= 333 (sut/part-1 sut/input)))
  (is (= 678536865274732 (sut/part-2 sut/input))))
