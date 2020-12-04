(ns aoc.2016.d12-test
  (:require [aoc.2016.d12 :as sut]
            [clojure.test :refer :all]))

(deftest challenges
  (is (= 318020 (sut/part-1 sut/input)))
  (is (= 9227674 (sut/part-2 sut/input))))
