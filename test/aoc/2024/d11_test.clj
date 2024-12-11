(ns aoc.2024.d11-test
    (:require
     [aoc.2024.d11 :as sut]
     [clojure.test :refer :all]))

(def example '(0 1 10 99 999))

(def example2 '(125 17))

(deftest examples
  (is (= 55312 (sut/part-1 example2))))

(deftest challenges
  (is (= 204022 (sut/part-1 sut/input)))
  (is (= 241651071960597 (sut/part-2 sut/input))))
