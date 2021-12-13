(ns aoc.2021.d13-test
  (:require [aoc.2021.d13 :as sut]
            [clojure.test :refer :all]))

(deftest challenges
  (is (= 755 (sut/part-1 sut/input)))
  (is (= (sut/part-2 sut/input)
         '("###  #    #  #   ## ###  ###   ##   ## "
           "#  # #    # #     # #  # #  # #  # #  #"
           "###  #    ##      # #  # ###  #  # #   "
           "#  # #    # #     # ###  #  # #### # ##"
           "#  # #    # #  #  # # #  #  # #  # #  #"
           "###  #### #  #  ##  #  # ###  #  #  ###"))))
