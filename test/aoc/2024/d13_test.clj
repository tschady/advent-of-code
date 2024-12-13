(ns aoc.2024.d13-test
    (:require
     [aoc.2024.d13 :as sut]
     [clojure.test :refer :all]))

(deftest challenges
  (is (= 36954 (sut/part-1 sut/input)))
  (is (= 79352015273424 (sut/part-2 sut/input))))
