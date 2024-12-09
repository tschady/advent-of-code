(ns aoc.2024.d09-test
    (:require
     [aoc.2024.d09 :as sut]
     [clojure.test :refer :all]))

(def example "2333133121414131402")

(deftest challenges
  (is (= 6225730762521 (sut/part-1 sut/input)))
  (is (= 6250605700557 (sut/part-2 sut/input))))
