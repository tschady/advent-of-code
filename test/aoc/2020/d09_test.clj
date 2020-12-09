(ns aoc.2020.d09-test
  (:require [aoc.2020.d09 :as sut]
            [clojure.test :refer :all]))

(deftest challenges
  (is (= 400480901 (sut/part-1 sut/input 25)))
  (is (= 67587168 (sut/part-2 sut/input 25))))
