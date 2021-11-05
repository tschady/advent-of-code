(ns aoc.2017.d11-test
  (:require [aoc.2017.d11 :as sut]
            [clojure.test :refer :all]))

(deftest challenges
  (is (= 743 (sut/part-1 sut/input)))
  (is (= 1493 (sut/part-2 sut/input))))
