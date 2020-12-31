(ns aoc.2020.d15-test
  (:require [aoc.2020.d15 :as sut]
            [clojure.test :as t :refer :all]))

(deftest challenges
  (is (= 257 (sut/part-1 sut/input)))
  (is (= 8546398 (sut/part-2 sut/input))))
