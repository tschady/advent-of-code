(ns aoc.2021.d06-test
  (:require [aoc.2021.d06 :as sut]
            [clojure.test :refer :all]))

(deftest challenges
  (is (= 351092 (sut/part-1 sut/input)))
  (is (= 1595330616005 (sut/part-2 sut/input))))
