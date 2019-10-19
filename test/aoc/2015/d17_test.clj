(ns aoc.2015.d17-test
  (:require [aoc.2015.d17 :as sut]
            [clojure.test :refer :all]))

(def denoms [20 15 10 5 5])

(deftest part-1-example
  (is (= 4 (sut/part-1 25 denoms))))

(deftest part-2-example
  (is (= 3 (sut/part-2 25 denoms))))

(deftest challenge
  (is (= 1304 (sut/part-1 150 sut/input)))
  (is (= 18 (sut/part-2 150 sut/input))))
