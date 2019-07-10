(ns aoc.2016.d06-test
  (:require [aoc.2016.d06 :as sut]
            [clojure.test :refer :all]))

(def test-input ["eedadn"
                 "drvtee"
                 "eandsr"
                 "raavrd"
                 "atevrs"
                 "tsrnev"
                 "sdttsa"
                 "rasrtv"
                 "nssdts"
                 "ntnada"
                 "svetve"
                 "tesnvt"
                 "vntsnd"
                 "vrdear"
                 "dvrsen"
                 "enarar"])

(deftest part1-examples
  (is (= "easter" (sut/part-1 test-input))))

(deftest part2-examples
  (is (= "advent" (sut/part-2 test-input))))

(deftest challenge
  (is (= "cyxeoccr" (sut/part-1 sut/input)))
  (is (= "batwpask" (sut/part-2 sut/input))))
