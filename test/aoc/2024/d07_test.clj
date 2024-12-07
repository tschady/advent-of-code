(ns aoc.2024.d07-test
    (:require
     [aoc.2024.d07 :as sut]
     [clojure.test :refer :all]))

(deftest challenges
  (is (= 3312271365652 (sut/part-1 sut/input)))
  (is (= 509463489296712 (sut/part-2 sut/input))))
