(ns aoc.2024.d12-test
    (:require
     [aoc.2024.d12 :as sut]
     [clojure.test :refer :all]))

(def ex1 ["AAAA"
          "BBCD"
          "BBCC"
          "EEEC"])

(def ex2 ["RRRRIICCFF"
          "RRRRIICCCF"
          "VVRRRCCFFF"
          "VVRCCCJFFF"
          "VVVVCJJCFE"
          "VVIVCCJJEE"
          "VVIIICJJEE"
          "MIIIIIJJEE"
          "MIIISIJEEE"
          "MMMISSJEEE"])

(deftest challenges
  (is (= 1473276 (sut/part-1 sut/input)))
  (is (= 901100 (sut/part-2 sut/input))))
