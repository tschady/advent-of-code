(ns aoc.2016.d20-test
  (:require [aoc.2016.d20 :as sut]
            [clojure.test :refer :all]))

(deftest challenges
  (is (= 22887907 (sut/part-1 sut/input)))
  (is (= 109 (sut/part-2 sut/input))))
