(ns aoc.2020.d22-test
  (:require [aoc.2020.d22 :as sut]
            [clojure.test :refer :all]))

(deftest score
  (is (= 306 (sut/deck-score [3, 2, 10, 6, 8, 5, 9, 4, 7, 1]))))

(deftest challenges
  (is (= 31308 (sut/part-1 sut/input)))
  (is (= 33647 (sut/part-2 sut/input))))
