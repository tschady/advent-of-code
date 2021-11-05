(ns aoc.2020.d25-test
  (:require [aoc.2020.d25 :as sut]
            [clojure.test :refer :all]))

(deftest challenges
  (is (= 16902792 (sut/part-1 sut/input))))
