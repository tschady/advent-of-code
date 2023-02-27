(ns aoc.2022.d20-test
    (:require
     [aoc.2022.d20 :as sut]
     [clojure.test :refer :all]))

(def example [1 2 -3 3 -2 0 4])

(deftest examples
  (is (= 3 (sut/part-1 example)))
  (is (= 1623178306 (sut/part-2 example))))

(deftest challenges
  (is (= 8764 (sut/part-1 sut/input)))
  (is (= 535648840980 (sut/part-2 sut/input))))
