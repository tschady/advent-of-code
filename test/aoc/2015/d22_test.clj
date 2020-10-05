(ns aoc.2015.d22-test
  (:require [aoc.2015.d22 :as sut]
            [clojure.test :refer :all]))

(deftest challenge
  (is (= 1269 (sut/part-1 sut/initial-state)))
  (is (= 1309 (sut/part-2 sut/initial-state))))
