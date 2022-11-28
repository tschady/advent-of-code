(ns aoc.2016.d25-test
  (:require [aoc.2016.d25 :as sut]
            [clojure.test :refer :all]))

(deftest challenges
  (is (= 182 (sut/part-1 sut/input))))
