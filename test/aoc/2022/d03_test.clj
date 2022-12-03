(ns aoc.2022.d03-test
  (:require [aoc.2022.d03 :as sut]
            [clojure.test :refer :all]))

(deftest challenges
  (is (= 7811 (sut/part-1 sut/input)))
  (is (= 2639 (sut/part-2 sut/input))))
