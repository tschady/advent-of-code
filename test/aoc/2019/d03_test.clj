(ns aoc.2019.d03-test
  (:require [aoc.2019.d03 :as sut]
            [clojure.test :refer :all]))

(deftest part1-examples
  (are [output input] (= output (sut/part-1 input))
    159 [["R75","D30","R83","U83","L12","D49","R71","U7","L72"]
         ["U62","R66","U55","R34","D71","R55","D58","R83"]]
    135 [["R98","U47","R26","D63","R33","U87","L62","D20","R33","U53","R51"]
         ["U98","R91","D20","R16","D67","R40","U7","R15","U6","R7"]]))

(deftest part2-examples
  (are [output input] (= output (sut/part-2 input))
    610 [["R75","D30","R83","U83","L12","D49","R71","U7","L72"]
         ["U62","R66","U55","R34","D71","R55","D58","R83"]]
    410 [["R98","U47","R26","D63","R33","U87","L62","D20","R33","U53","R51"]
         ["U98","R91","D20","R16","D67","R40","U7","R15","U6","R7"]]))

(deftest challenges
  (is (= 221 (sut/part-1 sut/input)))
  (is (= 18542 (sut/part-2 sut/input))))
