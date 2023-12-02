(ns aoc.2023.d02-test
    (:require
     [aoc.2023.d02 :as sut]
     [clojure.test :refer :all]))

(deftest challenges
  (is (= 2006 (sut/part-1 sut/input)))
  (is (= 84911 (sut/part-2 sut/input))))
