(ns aoc.2025.d02-test
    (:require
     [aoc.2025.d02 :as sut]
     [clojure.test :refer :all]))

(def example
  (partition 2(s/ints-pos "1-22,95-115,998-1012,1188511880-1188511890,222220-222224,
1698522-1698528,446443-446449,38593856-38593862,565653-565659,
824824821-824824827,2121212118-2121212124")))

(deftest challenges
  (is (= 40398804950 (sut/part-1 sut/input)))
  (is (= 65794984339 (sut/part-2 sut/input))))
