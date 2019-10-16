(ns aoc.2015.d15-test
  (:require [aoc.2015.d15 :as sut]
            [clojure.test :refer :all]))

(def test-input
  ["Butterscotch: capacity -1, durability -2, flavor 6, texture 3, calories 8"
   "Cinnamon: capacity 2, durability 3, flavor -2, texture -1, calories 3"])

(def parsed-input [[-1 -2 6 3 8]
                   [2 3 -2 -1 3]])

(deftest parse
  (is (= parsed-input (map sut/parse-line test-input))))

(deftest score
  (is (= 62842880 (:score (sut/bake-cookie parsed-input [44 56])))))

(deftest part-1-examples
  (is (= 62842880 (sut/part-1 test-input))))

(deftest part-2-examples
  (is (= 57600000 (sut/part-2 test-input))))

(deftest challenge
  (is (= 222870 (sut/part-1 sut/input)))
  (is (= 117936 (sut/part-2 sut/input))))
