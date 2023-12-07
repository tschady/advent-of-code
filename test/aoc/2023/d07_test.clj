(ns aoc.2023.d07-test
    (:require
     [aoc.2023.d07 :as sut]
     [clojure.test :refer :all]))

(deftest challenges
  (is (= 250347426 (sut/part-1 sut/input)))
  (is (= 251224870 (sut/part-2 sut/input))))
