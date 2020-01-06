(ns aoc.2019.d12-test
  (:require [aoc.2019.d12 :as sut]
            [clojure.test :refer :all]))

(deftest challenges
  (is (= 12351 (sut/part-1 sut/input 1000)))
  (is (= 380635029877596 (sut/part-2 sut/input))))
