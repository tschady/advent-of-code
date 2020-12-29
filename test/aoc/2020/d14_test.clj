(ns aoc.2020.d14-test
  (:require [aoc.2020.d14 :as sut]
            [clojure.test :refer :all]))

(deftest challenges
  (is (= 8332632930672N (sut/part-1 sut/input)))
  (is (= 4753238784664N (sut/part-2 sut/input))))
