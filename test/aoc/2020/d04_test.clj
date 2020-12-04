(ns aoc.2020.d04-test
  (:require [aoc.2020.d04 :as sut]
            [clojure.test :refer :all]))

(deftest challenges
  (is (= 233 (sut/part-1 sut/input)))
  (is (= 111 (sut/part-2 sut/input))))


