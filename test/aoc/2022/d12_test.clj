(ns aoc.2022.d12-test
    (:require
     [aoc.2022.d12 :as sut]
     [clojure.test :refer :all]))

(def example
  ["Sabqponm"
   "abcryxxl"
   "accszExk"
   "acctuvwj"
   "abdefghi"])

(deftest challenges
  (is (= 423 (sut/part-1 sut/input)))
  (is (= 416 (sut/part-2 sut/input))))
