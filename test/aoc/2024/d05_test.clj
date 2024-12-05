(ns aoc.2024.d05-test
    (:require
     [aoc.2024.d05 :as sut]
     [clojure.test :refer :all]))

(deftest challenges
  (is (= 6267 (sut/part-1 sut/input)))
  (is (= 5184 (sut/part-2 sut/input))))
