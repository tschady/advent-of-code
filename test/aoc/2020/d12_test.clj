(ns aoc.2020.d12-test
  (:require [aoc.2020.d12 :as sut]
            [clojure.test :refer :all]))

(deftest challenges
  (is (= 1956 (sut/part-1 sut/input)))
  (is (= 126797 (sut/part-2 sut/input))))
