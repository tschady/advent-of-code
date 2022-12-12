(ns aoc.2017.d16-test
    (:require
     [aoc.2017.d16 :as sut]
     [clojure.test :refer :all]))

(deftest challenges
  (is (= "jkmflcgpdbonihea" (sut/part-1 "abcdefghijklmnop" sut/input)))
  (is (= "ajcdefghpkblmion" (sut/part-2 "abcdefghijklmnop" sut/input))))
