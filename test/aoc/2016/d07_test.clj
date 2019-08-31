(ns aoc.2016.d07-test
  (:require [aoc.2016.d07 :as sut]
            [clojure.test :refer :all]))

(deftest part1-examples
  (is (true? (sut/tls? "abba[mnop]qrst")))
  (is (false? (sut/tls? "abcd[bddb]xyyx")))
  (is (false? (sut/tls? "aaaa[qwer]tyui")))
  (is (true? (sut/tls? "ioxxoj[asdfgh]zxcvbn"))))

(deftest part2-examples
  (is (true? (sut/ssl? "aba[bab]xyz")))
  (is (false? (sut/ssl? "xyx[xyx]xyx")))
  (is (true? (sut/ssl? "aaa[kek]eke")))
  (is (true? (sut/ssl? "zazbz[bzb]cdb"))))

(deftest challenge
  (is (= 105 (sut/part-1 sut/input)))
  (is (= 258 (sut/part-2 sut/input))))
