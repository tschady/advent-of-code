(ns aoc.2019.d04-test
  (:require [aoc.2019.d04 :as sut]
            [clojure.test :refer :all]))

(deftest challenges
  (is (= 454 (sut/part-1 sut/input-min sut/input-max)))
  (is (= 288 (sut/part-2 sut/input-min sut/input-max))))
