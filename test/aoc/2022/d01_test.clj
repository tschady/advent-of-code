(ns aoc.2022.d01-test
  (:require [aoc.2022.d01 :as sut]
            [clojure.test :refer :all]))

(deftest challenges
  (is (= 69795 (sut/part-1 sut/input)))
  (is (= 208437 (sut/part-2 sut/input))))
