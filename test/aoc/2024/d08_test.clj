(ns aoc.2024.d08-test
    (:require
     [aoc.2024.d08 :as sut]
     [clojure.test :refer :all]))

(def example ["............"
              "........0..."
              ".....0......"
              ".......0...."
              "....0......."
              "......A....."
              "............"
              "............"
              "........A..."
              ".........A.."
              "............"
              "............"])

(deftest examples
  (is (= 14 (sut/part-1 example)))
  (is (= 34 (sut/part-2 example))))

(deftest challenges
  (is (= 409 (sut/part-1 sut/input)))
  (is (= 1308 (sut/part-2 sut/input))))
