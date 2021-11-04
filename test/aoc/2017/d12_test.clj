(ns aoc.2017.d12-test
  (:require [aoc.2017.d12 :as sut]
            [clojure.test :refer :all]))

(deftest challenges
  (is (= 128 (sut/part-1 sut/input)))
  (is (= 209 (sut/part-2 sut/input))))
