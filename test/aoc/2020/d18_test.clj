(ns aoc.2020.d18-test
  (:require [aoc.2020.d18 :as sut]
            [clojure.test :as t :refer :all]))

(deftest examples
  (is (= 26 (sut/part-1 ["2 * 3 + (4 * 5)"])))
  (is (= 46 (sut/part-2 ["2 * 3 + (4 * 5)"]))))

(deftest challenges
  (is (= 75592527415659N (sut/part-1 sut/input)))
  (is (= 360029542265462N (sut/part-2 sut/input))))
