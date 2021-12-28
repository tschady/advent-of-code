(ns aoc.2021.d25-test
  (:require [aoc.2021.d25 :as sut]
            [clojure.test :refer :all]))

(deftest challenges
  (is (= 429 (sut/part-1 sut/input))))
