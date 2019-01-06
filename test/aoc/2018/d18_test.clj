(ns aoc.2018.d18-test
  (:require [aoc.2018.d18 :as sut]
            [clojure.test :refer :all]))

(def test-input
  [".#.#...|#."
   ".....#|##|"
   ".|..|...#."
   "..|#.....#"
   "#.#|||#|#|"
   "...#.||..."
   ".|....|..."
   "||...#|.#|"
   "|.||||..|."
   "...#.|..|."])

(deftest part1-examples
  (is (= 1147 (sut/part-1 test-input 10))))

(deftest challenge
  (is (= 531417 (sut/part-1 sut/input 10)))
  (is (= 205296 (sut/part-2 sut/input 1000000000))))
