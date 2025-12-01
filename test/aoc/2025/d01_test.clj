(ns aoc.2025.d01-test
  (:require [aoc.2025.d01 :as sut]
            [clojure.test :refer :all]))

(deftest challenges
  (is (= 1118 (sut/part-1 sut/input)))
  (is (= 6289 (sut/part-2 sut/input))))
