(ns aoc.2020.d06-test
  (:require [aoc.2020.d06 :as sut]
            [clojure.test :refer :all]))

(deftest challenges
  (is (= 6249 (sut/part-1 sut/input)))
  (is (= 3103 (sut/part-2 sut/input))))
