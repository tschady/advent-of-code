(ns aoc.2025.d06-test
    (:require
     [aoc.2025.d06 :as sut]
     [clojure.test :refer :all]))

(deftest challenges
  (is (= 6169101504608 (sut/part-1 sut/input)))
  (is (= 10442199710797 (sut/part-2 sut/input))))
