(ns aoc.2021.d20-test
  (:require [aoc.2021.d20 :as sut]
            [clojure.test :refer :all]))

(deftest challenges
  (is (= 5682 (sut/part-1 sut/input)))
  (is (= 17628 (sut/part-2 sut/input))))
