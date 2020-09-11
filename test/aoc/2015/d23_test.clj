(ns aoc.2015.d23-test
  (:require [aoc.2015.d23 :as sut]
            [clojure.test :refer :all]))

(deftest challenge
  (is (= 170 (sut/part-1 sut/input)))
  (is (= 247 (sut/part-2 sut/input))))
