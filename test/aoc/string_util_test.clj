(ns aoc.string-util-test
  (:require [aoc.string-util :as sut]
            [clojure.test :refer :all]))

(deftest explode-digits
  (is (= '(5 3 1 2 4) (sut/explode-digits "53124"))))

(deftest anagram?
  (testing "Expect identical word to be false"
    (is (= false (sut/anagram? "foo" "foo"))))
  (testing "Expect different words to be false"
    (is (= false (sut/anagram? "foo" "foo "))))
  (testing "Expect scrambled words to be true"
    (is (= true (sut/anagram? "theflowerflat" "heartfeltwolf"))))
  (testing "It should be case insensitive"
    (is (= true (sut/anagram? "AbCd" "DBca")))))

(deftest hamming-distance
  (testing "Expect unequal inputs to return 'nil'"
    (is (= nil (sut/hamming-distance "abc" "abcd"))))
  (testing "Expect identical strings to return 0"
    (is (= 0 (sut/hamming-distance "abcdef" "abcdef"))))
  (testing "Expect correct difference calc"
    (is (= 3 (sut/hamming-distance "aBCDef" "aXYZef")))))

(deftest string-intersection
  (testing "Expect disjoint strings to return empty string"
    (is (= "" (sut/string-intersection "abc" "xyz"))))
  (testing "Expect correct intersection calc"
    (is (= "abd" (sut/string-intersection "1a2b3C4d" "5a6b7c8d")))))
