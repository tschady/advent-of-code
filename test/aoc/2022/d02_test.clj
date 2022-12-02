(ns aoc.2022.d02-test
  (:require [aoc.2022.d02 :as sut]
            [clojure.test :refer :all]))

(deftest challenges
  (is (= 11841 (sut/part-1 sut/input)))
  (is (= 13022 (sut/part-2 sut/input))))
