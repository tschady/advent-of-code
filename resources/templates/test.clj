(ns aoc.{{year}}.d{{day}}-test
    (:require
     [aoc.{{year}}.d{{day}} :as sut]
     [clojure.test :refer :all]))

(deftest challenges
  (is (= false (sut/part-1 sut/input)))
  (is (= false (sut/part-2 sut/input))))
