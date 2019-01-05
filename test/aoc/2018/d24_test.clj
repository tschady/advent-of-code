(ns aoc.2018.d24-test
  (:require [aoc.2018.d24 :as sut]
            [clojure.test :refer :all]))

(def test-input
  "Immune System:
17 units each with 5390 hit points (weak to radiation, bludgeoning) with an attack that does 4507 fire damage at initiative 2
989 units each with 1274 hit points (immune to fire; weak to bludgeoning, slashing) with an attack that does 25 slashing damage at initiative 3
42 units each with 12 hit points with an attack that does 2 slashing damage at initiative 7

Infection:
801 units each with 4706 hit points (weak to radiation) with an attack that does 116 bludgeoning damage at initiative 1
4485 units each with 2961 hit points (immune to radiation; weak to fire, cold) with an attack that does 12 slashing damage at initiative 4
")

(deftest part1-examples
  (is (= 5216 (sut/part-1 test-input))))

;; (deftest challenge
;;   (is (= 104712 (sut/part-1 sut/input)))
;;   (is (= 840 (sut/part-2 sut/input))))
