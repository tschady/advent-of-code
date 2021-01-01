(ns aoc.2020.d17-test
  (:require [aoc.2020.d17 :as sut]
            [clojure.test :as t :refer :all]))

(deftest challenges
  (is (= 298 (sut/part-1 sut/input)))
  (is (= 1792 (sut/part-2 sut/input))))
