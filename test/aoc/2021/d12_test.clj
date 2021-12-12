(ns aoc.2021.d12-test
  (:require [aoc.2021.d12 :as sut]
            [clojure.test :refer :all]))

(def ex ["start-A"
         "start-b"
         "A-c"
         "A-b"
         "b-d"
         "A-end"
         "b-end"])

(deftest challenges
  (is (= 4912 (sut/part-1 sut/input)))
  (is (= 150004 (sut/part-2 sut/input))))
