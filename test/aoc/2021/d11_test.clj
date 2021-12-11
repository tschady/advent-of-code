(ns aoc.2021.d11-test
  (:require [aoc.2021.d11 :as sut]
            [clojure.test :refer :all]))

(deftest challenges
  (is (= 1705 (sut/part-1 sut/input)))
  (is (= 265 (sut/part-2 sut/input))))
