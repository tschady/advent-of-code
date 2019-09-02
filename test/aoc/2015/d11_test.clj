(ns aoc.2015.d11-test
  (:require [aoc.2015.d11 :as sut]
            [clojure.test :refer :all]))

(deftest part-1-examples
  (are [input output] (= output (sut/inc-pass input))
    "xx" "xy"
    "xy" "xz"
    "xz" "ya"
    "ya" "yb"))

(deftest challenge
  (is (= "hepxxyzz" (sut/part-1)))
  (is (= "heqaabcc" (sut/part-2))))
