(ns aoc.2018.d02-test
  (:require [aoc.2018.d02 :as sut]
            [clojure.test :refer :all]))

(deftest part1-examples
  (is (= 12 (sut/part-1 ["abcdef"
                         "bababc"
                         "abbcde"
                         "abcccd"
                         "aabcdd"
                         "abcdee"
                         "ababab"]))))

(deftest part2-examples
  (is (= "fgij" (sut/part-2 ["abcde"
                             "fghij"
                             "klmno"
                             "pqrst"
                             "fguij"
                             "axcye"
                             "wvxyz"]))))

(deftest challenge
  (is (= 6370 (sut/part-1 sut/input)))
  (is (= "rmyxgdlihczskunpfijqcebtv" (sut/part-2 sut/input))))
