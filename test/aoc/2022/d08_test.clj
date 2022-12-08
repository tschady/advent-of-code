(ns aoc.2022.d08-test
  (:require
   [aoc.2022.d08 :as sut]
   [aoc.string-util :as s]
   [clojure.string :as str]
   [clojure.test :refer :all]))

(def ex (->> "30373
25512
65332
33549
35390"
             str/split-lines
             (map s/explode-digits)))

(deftest examples
  (is (= 21 (sut/part-1 ex)))
  (is (= 8 (sut/part-2 ex))))

(deftest challenges
  (is (= 1785 (sut/part-1 sut/input)))
  (is (= 345168 (sut/part-2 sut/input))))
