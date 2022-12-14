(ns aoc.2022.d14-test
    (:require
     [aoc.2022.d14 :as sut]
     [clojure.test :refer :all]))

(deftest challenges
  (is (= 885 (sut/part-1 sut/input)))
  (is (= 28691 (sut/part-2 sut/input))))
