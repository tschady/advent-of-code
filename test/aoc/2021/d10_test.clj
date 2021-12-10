(ns aoc.2021.d10-test
  (:require [aoc.2021.d10 :as sut]
            [clojure.test :refer :all]))

(deftest challenges
  (is (= 392097 (sut/part-1 sut/input)))
  (is (= 4263222782 (sut/part-2 sut/input))))
