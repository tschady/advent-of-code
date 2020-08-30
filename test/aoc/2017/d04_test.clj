(ns aoc.2017.d04-test
  (:require [aoc.2017.d04 :as sut]
            [aoc.string-util :as string-util]
            [clojure.test :refer :all]))

(deftest part-1-examples
  (are [output input] (= output (sut/valid-phrase? identity input))
    true  "aa bb cc dd ee"
    false "aa bb cc dd aa"
    true  "aa bb cc dd aaa"))

(deftest part-2-examples
  (are [output input] (= output (sut/valid-phrase? string-util/alphagram input))
    true  "abcde fghij"
    false "abcde xyz ecdab"
    true  "a ab abc abd abf abj"
    true  "iiii oiii ooii oooi oooo"
    false "oiii ioii iioi iiio"))

(deftest challenge
  (is (= 337 (sut/part-1 sut/input)))
  (is (= 231 (sut/part-2 sut/input))))
