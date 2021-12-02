(ns aoc.2021.d02-test
  (:require [aoc.2021.d02 :as sut]
            [clojure.test :refer :all]))

((deftest challenges
   (is (= 1882980 (sut/part-1 sut/input)))
   (is (= 1971232560 (sut/part-2 sut/input)))))
