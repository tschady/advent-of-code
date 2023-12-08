(ns aoc.2023.d08-test
    (:require
     [aoc.2023.d08 :as sut]
     [clojure.test :refer :all]))

(deftest challenges
  (is (= 17621 (sut/part-1 sut/input)))
  (is (= 20685524831999 (sut/part-2 sut/input))))
