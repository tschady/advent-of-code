(ns aoc.2020.d19-test
  (:require [aoc.2020.d19 :as sut]
            [clojure.test :refer :all]))

(deftest challenges
  (is (= 235 (sut/part-1 sut/input)))
  (is (= 379 (sut/part-2 sut/input))))
