(ns aoc.2021.d05-test
  (:require [aoc.2021.d05 :as sut]
            [clojure.test :refer :all]))

(deftest challenges
  (is (= 8111 (sut/part-1 sut/input)))
  (is (= 22088 (sut/part-2 sut/input))))
