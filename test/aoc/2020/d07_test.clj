(ns aoc.2020.d07-test
  (:require [aoc.2020.d07 :as sut]
            [clojure.test :refer :all]))

(deftest challenges
  (is (= 348 (sut/part-1 sut/input)))
  (is (= 18885 (sut/part-2 sut/input))))


