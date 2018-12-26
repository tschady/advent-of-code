(ns aoc.2018.d04-test
  (:require [aoc.2018.d04 :as sut]
            [clojure.test :refer :all]))

(def test-log ["[1518-11-01 00:00] Guard #10 begins shift"
               "[1518-11-01 00:05] falls asleep"
               "[1518-11-01 00:25] wakes up"
               "[1518-11-01 00:30] falls asleep"
               "[1518-11-01 00:55] wakes up"
               "[1518-11-01 23:58] Guard #99 begins shift"
               "[1518-11-02 00:40] falls asleep"
               "[1518-11-02 00:50] wakes up"
               "[1518-11-03 00:05] Guard #10 begins shift"
               "[1518-11-03 00:24] falls asleep"
               "[1518-11-03 00:29] wakes up"
               "[1518-11-04 00:02] Guard #99 begins shift"
               "[1518-11-04 00:36] falls asleep"
               "[1518-11-04 00:46] wakes up"
               "[1518-11-05 00:03] Guard #99 begins shift"
               "[1518-11-05 00:45] falls asleep"
               "[1518-11-05 00:55] wakes up"])

(deftest part1-examples
  (is (= 240 (sut/part-1 test-log))))

(deftest part2-examples
  (is (= 4455 (sut/part-2 test-log))))

(deftest challenge
  (is (= 94542 (sut/part-1 sut/input)))
  (is (= 50966 (sut/part-2 sut/input))))
