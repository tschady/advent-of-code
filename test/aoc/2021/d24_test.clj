(ns aoc.2021.d24-test
  (:require [aoc.2021.d24 :as sut]
            [clojure.test :refer :all]))

(deftest challenges
  (is (= "99691891979938" (sut/part-1 sut/input)))
  (is (= "27141191213911" (sut/part-2 sut/input))))
