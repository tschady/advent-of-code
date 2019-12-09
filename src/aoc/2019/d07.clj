(ns aoc.2019.d07
  (:require [aoc.2019.intcode :as intcode]
            [aoc.file-util :as file-util]
            [clojure.math.combinatorics :as combo]
            [clojure.string :as str]))

(def input (mapv read-string (str/split ( file-util/read-file "2019/d07.txt") #",")))

#_(:out (intcode/run-prog (intcode/make-prog input [1])))

#_(:out (intcode/run-prog (intcode/make-prog input [5])))

(defn pipeline [tape combos]
  (reduce (fn [last-output phase]
            (-> tape
                (intcode/make-prog [phase last-output])
                intcode/run-prog
                :out
                peek))
          0
          combos))

(pipeline input [3 2 1 4 0])

(->> (range 5)
     combo/permutations
     (map (partial pipeline input))
     (apply max)) ;; 101490



(def tp [3,26,1001,26,-4,26,3,27,1002,27,2,27,1,27,26,
         27,4,27,1001,28,-1,28,1005,28,6,99,0,0,5])

; (pipeline tp [9 8 7 6 5])

(intcode/run-prog (intcode/make-prog tp [9 0]))
