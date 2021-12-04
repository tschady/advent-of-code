(ns aoc.2021.d04-test
  (:require [aoc.2021.d04 :as sut]
            [clojure.test :refer :all]))

(deftest challenges
  (is (= 8442 (sut/part-1 sut/input)))
  (is (= 4590 (sut/part-2 sut/input))))
