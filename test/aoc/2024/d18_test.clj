(ns aoc.2024.d18-test
    (:require
     [aoc.2024.d18 :as sut]
     [clojure.test :refer :all]))

(deftest challenges
  (is (= 272 (sut/part-1 sut/input)))
  (is (= "16,44" (sut/part-2 sut/input))))
