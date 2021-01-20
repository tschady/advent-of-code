(ns aoc.2017.d09-test
  (:require [aoc.2017.d09 :as sut]
            [clojure.test :refer :all]))

(deftest challenges
  (is (= 7640 (sut/part-1 sut/input)))
  (is (= 4368 (sut/part-2 sut/input))))
