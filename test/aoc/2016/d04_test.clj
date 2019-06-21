(ns aoc.2016.d04-test
  (:require [aoc.2016.d04 :as sut]
            [clojure.test :refer :all]))

(def test-input ["aaaaa-bbb-z-y-x-123[abxyz]"
                 "a-b-c-d-e-f-g-h-987[abcde]"
                 "not-a-real-room-404[oarel]"
                 "totally-real-room-200[decoy]"])

(deftest part1-examples
  (is (= 1514 (sut/part-1 test-input))))

(deftest decrypt
  (is (= "veryencryptedname" (sut/decrypt "qzmtzixmtkozyivhz" 343))))

(deftest challenge
  (is (= 173787 (sut/part-1 sut/input)))
  (is (= 548 (sut/part-2 sut/input))))
