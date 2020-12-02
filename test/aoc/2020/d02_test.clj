(ns aoc.2020.d02-test
  (:require [aoc.2020.d02 :as sut]
            [clojure.test :refer :all]))

(deftest challenges
  (is (= 467 (sut/part-1 sut/input)))
  (is (= 441 (sut/part-2 sut/input))))
