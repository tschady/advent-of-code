(ns aoc.2020.d21-test
  (:require [aoc.2020.d21 :as sut]
            [clojure.test :refer :all]))

(def test-input ["mxmxvkd kfcds sqjhc nhms (contains dairy, fish)"
                 "trh fvjkl sbzzf mxmxvkd (contains dairy)"
                 "sqjhc fvjkl (contains soy)"
                 "sqjhc mxmxvkd sbzzf (contains fish)"])

(deftest challenges
  (is (= 2786 (sut/part-1 sut/input)))
  (is (= "prxmdlz,ncjv,knprxg,lxjtns,vzzz,clg,cxfz,qdfpq" (sut/part-2 sut/input))))
