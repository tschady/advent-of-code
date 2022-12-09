(ns aoc.2022.d09-test
    (:require
     [aoc.2022.d09 :as sut]
     [clojure.test :refer :all]))

(def ex-1 (str/split-lines "R 4
U 4
L 3
D 1
R 4
D 1
L 5
R 2"))

(def ex-2 (str/split-lines
          "R 5
U 8
L 8
D 3
R 17
D 10
L 25
U 20"))

(deftest challenges
  (is (= 6269 (sut/part-1 sut/input)))
  (is (= 2557 (sut/part-2 sut/input))))
