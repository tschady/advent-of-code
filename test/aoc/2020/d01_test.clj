(ns aoc.2020.d01-test
  (:require [aoc.2020.d01 :as sut]
            [clojure.test :refer :all]))

(deftest challenges
  (is (= 1010299 (sut/part-1 sut/input)))
  (is (= 42140160 (sut/part-2 sut/input))))
