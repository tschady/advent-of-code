(ns aoc.2020.d13-test
  (:require [aoc.2020.d13 :as sut]
            [clojure.test :refer :all]))

(deftest examples
  (is (= 1068781 (sut/part-2 ["_" "7,13,x,x,59,x,31,19"]))))

(deftest challenges
  (is (= 3246 (sut/part-1 sut/input)))
  (is (= 1010182346291467 (sut/part-2 sut/input))))
