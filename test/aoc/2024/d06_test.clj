(ns aoc.2024.d06-test
    (:require
     [aoc.2024.d06 :as sut]
     [clojure.test :refer :all]))

(def example ["....#....."
              ".........#"
              ".........."
              "..#......."
              ".......#.."
              ".........."
              ".#..^....."
              "........#."
              "#........."
              "......#..."])

(deftest examples
  (is (= 41 (sut/part-1 example)))
  (is (= 6 (sut/part-2 example))))

(deftest challenges
  (is (= 4826 (sut/part-1 sut/input)))
  (is (= 1721 (sut/part-2 sut/input))))
