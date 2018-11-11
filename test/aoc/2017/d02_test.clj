(ns aoc.2017.d02-test
  (:require [aoc.2017.d02 :as sut]
            [clojure.test :refer :all]))

(def example-input '((5 1 9 5)
                     (7 5 3)
                     (2 4 6 8)))

(def example-input2 '((5 9 2 8)
                      (9 4 7 3)
                      (3 8 6 5)))

(deftest part-examples
  (is (= 18 (sut/part-1 example-input)))
  (is (= 9 (sut/part-2 example-input2))))

(deftest challenge
  (is (= 54426 (sut/part-1 sut/input)))
  (is (= 333 (sut/part-2 sut/input))))
