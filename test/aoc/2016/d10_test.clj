(ns aoc.2016.d10-test
  (:require [aoc.2016.d10 :as sut]
            [clojure.test :refer :all]))

(deftest challenge
  (is (= "bot 56" (sut/part-1 sut/input)))
  (is (= 7847 (sut/part-2 sut/input))))
