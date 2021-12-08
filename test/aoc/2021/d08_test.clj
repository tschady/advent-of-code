(ns aoc.2021.d08-test
  (:require [aoc.2021.d08 :as sut]
            [clojure.test :refer :all]))

(deftest challenges
  (is (= 352 (sut/part-1 sut/input)))
  (is (= 936117 (sut/part-2 sut/input))))
