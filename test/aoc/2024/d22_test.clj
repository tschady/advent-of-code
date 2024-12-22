(ns aoc.2024.d22-test
    (:require
     [aoc.2024.d22 :as sut]
     [clojure.test :refer :all]))

(deftest challenges
  (is (= 17724064040 (sut/part-1 sut/input)))
  (is (= 1998 (sut/part-2 sut/input))))
