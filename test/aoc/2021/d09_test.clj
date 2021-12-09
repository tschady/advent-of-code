(ns aoc.2021.d09-test
  (:require [aoc.2021.d09 :as sut]
            [clojure.test :refer :all]))

(deftest challenges
  (is (= 468 (sut/part-1 sut/input)))
  (is (= 1280496 (sut/part-2 sut/input))))
