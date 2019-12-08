(ns aoc.2019.d02-test
  (:require [aoc.2019.d02 :as sut]
            [clojure.test :refer :all]))

(deftest challenges
  (is (= 4930687 (sut/part-1 sut/input)))
  (is (= 5335 (sut/part-2 sut/input))))
