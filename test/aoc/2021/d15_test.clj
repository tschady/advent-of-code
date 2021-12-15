(ns aoc.2021.d15-test
  (:require [aoc.2021.d15 :as sut]
            [clojure.test :refer :all]))

(def ex ["1163751742"
         "1381373672"
         "2136511328"
         "3694931569"
         "7463417111"
         "1319128137"
         "1359912421"
         "3125421639"
         "1293138521"
         "2311944581"])

(deftest challenges
  (is (= 824 (sut/part-1 sut/input)))
  (is (= 3063 (sut/part-2 sut/input))))
