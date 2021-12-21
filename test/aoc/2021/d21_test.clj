(ns aoc.2021.d21-test
  (:require [aoc.2021.d21 :as sut]
            [clojure.test :refer :all]))

#_(deftest examples
  (is (= 739785 (sut/part-1 [4 8])))
  (is (= 444356092776315 (sut/part-2 [4 8]))))

(deftest challenges
  (is (= 929625 (sut/part-1 sut/input)))
  (is (= 175731756652760 (sut/part-2 sut/input))))
