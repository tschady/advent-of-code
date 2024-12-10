(ns aoc.2024.d10-test
    (:require
     [aoc.2024.d10 :as sut]
     [clojure.test :refer :all]))

(def example ["89010123"
              "78121874"
              "87430965"
              "96549874"
              "45678903"
              "32019012"
              "01329801"
              "10456732"])

(deftest challenges
  (is (= 472 (sut/part-1 sut/input)))
  (is (= 969 (sut/part-2 sut/input))))
