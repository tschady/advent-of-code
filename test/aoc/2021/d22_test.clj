(ns aoc.2021.d22-test
  (:require [aoc.2021.d22 :as sut]
            [clojure.test :refer :all]))

(deftest challenges
  (is (= 527915 (sut/part-1 sut/input)))
  (is (= 1218645427221987 (sut/part-2 sut/input))))
