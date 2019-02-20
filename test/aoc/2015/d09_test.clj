(ns aoc.2015.d09-test
  (:require [aoc.2015.d09 :as sut]
            [clojure.test :refer :all]))

(deftest part-1-examples
  (is (= 605 (sut/part-1 ["London to Dublin = 464"
                          "London to Belfast = 518"
                          "Dublin to Belfast = 141"]))))

(deftest part-1-examples
  (is (= 982 (sut/part-2 ["London to Dublin = 464"
                          "London to Belfast = 518"
                          "Dublin to Belfast = 141"]))))

(deftest challenge
  (is (= 207 (sut/part-1 sut/input)))
  (is (= 804 (sut/part-2 sut/input))))
