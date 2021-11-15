(ns aoc.2020.d24-test
  (:require [aoc.2020.d24 :as sut]
            [clojure.test :refer :all]))

(deftest ^:slow challenges
  (is (= 420 (sut/part-1 sut/input)))
  (is (= 4206 (sut/part-2 sut/input))))
