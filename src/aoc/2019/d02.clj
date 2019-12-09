(ns aoc.2019.d02
  (:require [aoc.file-util :as file-util]
            [aoc.2019.intcode :as intcode]
            [clojure.string :as str]))

(def input (mapv read-string (str/split (file-util/read-file "2019/d02.txt") #",")))

(defn part-1 [input] (-> input
                         (intcode/set-noun-verb 12 2)
                         intcode/make-prog
                         intcode/run-prog
                         :mem
                         first))

(defn part-2 [input]
  (let [magic-output 19690720]
    (first (for [noun  (range 100)
                 verb  (range 100)
                 :let  [mem (-> input
                                (intcode/set-noun-verb noun verb)
                                intcode/make-prog
                                intcode/run-prog
                                :mem)]
                 :when (= magic-output (first mem))]
             (+ verb (* 100 noun))))))
