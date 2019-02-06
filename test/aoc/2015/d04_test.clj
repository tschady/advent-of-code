(ns aoc.2015.d04-test
  (:require [aoc.2015.d04 :as sut]
            [clojure.test :refer :all]))

(deftest part-1-examples
  (are [output input] (= output (sut/part-1 input))
    609043 "abcdef"
    1048970 "pqrstuv"))

(deftest challenge
  (is (= 346386 (sut/part-1 sut/input)))
  (is (= 9958218 (sut/part-2 sut/input))))
