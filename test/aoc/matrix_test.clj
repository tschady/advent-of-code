(ns aoc.matrix-test
  (:require [aoc.matrix :as sut]
            [clojure.test :refer :all]))

(deftest rotate-seq-left
  (is (= (seq "xooo") (sut/rotate-seq-left 1 "oxoo")))
  (is (= (seq "ooox") (sut/rotate-seq-left 10 "oxoo"))))

(deftest rotate-seq-right
  (is (= (seq "ooxo") (sut/rotate-seq-right 1 "oxoo"))))
