(ns aoc.2022.d04-test
  (:require [aoc.2022.d04 :as sut]
            [clojure.test :refer :all]))

(deftest challenges
  (is (= 464 (sut/part-1 sut/input)))
  (is (= 770 (sut/part-2 sut/input))))
