(ns aoc.2023.d11-test
    (:require
     [aoc.2023.d11 :as sut]
     [clojure.test :refer :all]))

(def ex
  ["...#......"
   ".......#.."
   "#........."
   ".........."
   "......#..."
   ".#........"
   ".........#"
   ".........."
   ".......#.."
   "#...#....."])

(deftest challenges
  (is (= 10231178 (sut/part-1 sut/input)))
  (is (= 622120986954 (sut/part-2 sut/input))))
