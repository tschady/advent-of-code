(ns aoc.2020.d11-test
  (:require [aoc.2020.d11 :as sut]
            [clojure.test :refer :all]))

(deftest challenges
  (is (= 2361 (sut/part-1 sut/input)))
  (is (= 2119 (sut/part-2 sut/input))))
