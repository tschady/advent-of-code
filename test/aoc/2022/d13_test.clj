(ns aoc.2022.d13-test
    (:require
     [aoc.2022.d13 :as sut]
     [clojure.test :refer :all]))

(def ex "[1,1,3,1,1]
[1,1,5,1,1]

[[1],[2,3,4]]
[[1],4]

[9]
[[8,7,6]]

[[4,4],4,4]
[[4,4],4,4,4]

[7,7,7,7]
[7,7,7]

[]
[3]

[[[]]]
[[]]

[1,[2,[3,[4,[5,6,7]]]],8,9]
[1,[2,[3,[4,[5,6,0]]]],8,9]")

(deftest challenges
  (is (= 6420 (sut/part-1 sut/input)))
  (is (= 22000 (sut/part-2 sut/input))))
