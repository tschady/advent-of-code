(ns aoc.2016.d14-test
  (:require [aoc.2016.d14 :as sut]
            [clojure.test :refer :all]))

(deftest stretched-hash
  (is (= "a107ff634856bb300138cac6568c0f24" (sut/stretched-hashcode "abc" 0))))

(deftest challenges
  (is (= 18626 (sut/part-1 sut/input)))
  (is (= 20092 (sut/part-2 sut/input))))
