(ns aoc.2024.d04-test
    (:require
     [aoc.2024.d04 :as sut]
     [clojure.test :refer :all]))

(def example ["MMMSXXMASM"
              "MSAMXMSMSA"
              "AMXSXMAAMM"
              "MSAMASMSMX"
              "XMASAMXAMM"
              "XXAMMXXAMA"
              "SMSMSASXSS"
              "SAXAMASAAA"
              "MAMMMXMMMM"
              "MXMXAXMASX"])

(deftest examples
  (is (= 18 (sut/part-1 example)))
  (is (= 9 (sut/part-2 example))))

(deftest challenges
  (is (= 2644 (sut/part-1 sut/input)))
  (is (= 1952 (sut/part-2 sut/input))))
