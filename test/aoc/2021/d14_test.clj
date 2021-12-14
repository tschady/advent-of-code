(ns aoc.2021.d14-test
  (:require [aoc.2021.d14 :as sut]
            [clojure.test :refer :all]))

(deftest challenges
  (is (= 3697 (sut/part-1 sut/input)))
  (is (= 4371307836157 (sut/part-2 sut/input))))
