(ns aoc.2016.d19-test
  (:require [aoc.2016.d19 :as sut]
            [clojure.test :refer :all]))

(deftest challenge
  (is (= 1842613 (sut/part-1 sut/input)))
  (is (= 1424135 (sut/part-2 sut/input))))
