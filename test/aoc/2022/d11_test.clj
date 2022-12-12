(ns aoc.2022.d11-test
  (:require
   [aoc.2022.d11 :as sut]
   [clojure.string :as str]
   [clojure.test :refer :all]))

(def ex1 (->
"Monkey 0:
  Starting items: 79, 98
  Operation: new = old * 19
  Test: divisible by 23
    If true: throw to monkey 2
    If false: throw to monkey 3

Monkey 1:
  Starting items: 54, 65, 75, 74
  Operation: new = old + 6
  Test: divisible by 19
    If true: throw to monkey 2
    If false: throw to monkey 0

Monkey 2:
  Starting items: 79, 60, 97
  Operation: new = old * old
  Test: divisible by 13
    If true: throw to monkey 1
    If false: throw to monkey 3

Monkey 3:
  Starting items: 74
  Operation: new = old + 3
  Test: divisible by 17
    If true: throw to monkey 0
    If false: throw to monkey 1"
(str/split #"\n\n")))

(deftest challenges
  (is (= 112815 (sut/part-1 sut/input)))
  (is (= 25738411485 (sut/part-2 sut/input))))
