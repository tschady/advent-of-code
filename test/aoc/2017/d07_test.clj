(ns aoc.2017.d07-test
  (:require [aoc.2017.d07 :as sut]
            [clojure.test :refer :all]))

(def test-graph
"pbga (66)
xhth (57)
ebii (61)
havc (66)
ktlj (57)
fwft (72) -> ktlj, cntj, xhth
qoyq (66)
padx (45) -> pbga, havc, qoyq
tknk (41) -> ugml, padx, fwft
jptl (61)
ugml (68) -> gyxo, ebii, jptl
gyxo (61)
cntj (57)")

(deftest part-1
  (is (= "tknk" (sut/part-1 test-graph))))

(deftest part-2
  (is (= 60 (sut/part-2 test-graph))))

(deftest challenge
  (is (= "cqmvs" (sut/part-1 sut/input)))
  (is (= 2310 (sut/part-2 sut/input))))
