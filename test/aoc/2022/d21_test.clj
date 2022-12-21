(ns aoc.2022.d21-test
    (:require
     [aoc.2022.d21 :as sut]
     [clojure.test :refer :all]))

(def example "root: pppw + sjmn
dbpl: 5
cczh: sllz + lgvd
zczc: 2
ptdq: humn - dvpt
dvpt: 3
lfqf: 4
humn: 5
ljgn: 2
sjmn: drzm * dbpl
sllz: 4
pppw: cczh / lfqf
lgvd: ljgn * ptdq
drzm: hmdt - zczc
hmdt: 32")

(deftest examples
  (is (= 152 (sut/part-1 example)))
  (is (= 301 (sut/part-1 example))))

(deftest challenges
  (is (= 80326079210554 (sut/part-1 sut/input)))
  (is (= 3617613952378 (sut/part-2 sut/input))))
