(ns aoc.2016.d23-test
  (:require [aoc.2016.d23 :as sut]
            [clojure.test :refer :all]))

(deftest ^:slow challenges
  (is (= 12663 (sut/part-1 sut/input)))
  (is (= 479009223 (sut/part-2 sut/input))))
