(ns aoc.2019.intcode-test
  (:require [aoc.2019.intcode :as sut]
            [clojure.test :refer :all]))

(deftest run-prog
  #_(testing "2019 Day 2 examples, part1"
    (are [input output] (= output (->> input
                                      sut/make-prog
                                      sut/run-prog
                                      :mem))
      [1,0,0,0,99]          [2,0,0,0,99]
      [2,3,0,3,99]          [2,3,0,6,99]
      [2,4,4,5,99,0]        [2,4,4,5,99,9801]
      [1,1,1,4,99,5,6,0,99] [30,1,1,4,2,5,6,0,99]))

  (testing "2019 Day 9 examples, part1"
    (is (= [109,1,204,-1,1001,100,1,100,1008,100,16,101,1006,101,0,99]
           (-> [109,1,204,-1,1001,100,1,100,1008,100,16,101,1006,101,0,99]
               sut/make-prog
               sut/run-prog
               :out)))
    
    ))
