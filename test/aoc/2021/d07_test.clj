(ns aoc.2021.d07-test
  (:require [aoc.2021.d07 :as sut]
            [clojure.test :refer :all]))

(deftest challenges
  (is (= 335330 (sut/part-1 sut/input)))
  (is (= 92439766 (sut/part-2 sut/input))))
