(ns aoc.2023.d03-test
    (:require
     [aoc.2023.d03 :as sut]
     [clojure.test :refer :all]))

(def ex ["467..114.."
         "...*......"
         "..35..633."
         "......#..."
         "617*......"
         ".....+.58."
         "..592....."
         "......755."
         "...$.*...."
         ".664.598.."])

(deftest examples
  (is (= 4361 (sut/part-1 ex)))
  (is (= 467835 (sut/part-2 ex))))

(deftest challenges
  (is (= 520019 (sut/part-1 sut/input)))
  (is (= 75519888 (sut/part-2 sut/input))))
