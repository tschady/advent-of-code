(ns aoc.2023.d05-test
  (:require
   [aoc.2023.d05 :as sut]
   [clojure.string :as str]
   [clojure.test :refer :all]))

(def ex-str "seeds: 79 14 55 13

seed-to-soil map:
50 98 2
52 50 48

soil-to-fertilizer map:
0 15 37
37 52 2
39 0 15

fertilizer-to-water map:
49 53 8
0 11 42
42 0 7
57 7 4

water-to-light map:
88 18 7
18 25 70

light-to-temperature map:
45 77 23
81 45 19
68 64 13

temperature-to-humidity map:
0 69 1
1 0 69

humidity-to-location map:
60 56 37
56 93 4")

(def ex (str/split ex-str #"\n\n"))

(deftest challenges
  (is (= 178159714 (sut/part-1 sut/input)))
  (is (= 100165128 (sut/part-2 sut/input))))
