(ns aoc.2016.d17-test
  (:require [aoc.2016.d17 :as sut]
            [clojure.test :refer :all]))

(deftest dead-end
  (is (= :no-solution (sut/part-1 "hijkl"))))

(deftest part-1-examples
  (are [input output] (= output (sut/part-1 input))
    "ihgpwlah" "DDRRRD"
    "kglvqrro" "DDUDRLRRUDRD"
    "ulqzkmiv" "DRURDRUDDLLDLUURRDULRLDUUDDDRR"))

(deftest part-2-examples
  (are [input output] (= output (sut/part-2 input))
    "ihgpwlah" 370
    "kglvqrro" 492
    "ulqzkmiv" 830))

(deftest challenge
  (is (= "DDRLRRUDDR" (sut/part-1 sut/input)))
  (is (= 556 (sut/part-2 sut/input))))
