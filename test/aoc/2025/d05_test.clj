(ns aoc.2025.d05-test
    (:require
     [aoc.2025.d05 :as sut]
     [clojure.test :refer :all]))

(deftest challenges
  (is (= 798 (sut/part-1 sut/input)))
  (is (= 366181852921027 (sut/part-2 sut/input))))
