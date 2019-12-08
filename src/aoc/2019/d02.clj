(ns aoc.2019.d02
  (:require [aoc.file-util :as file-util]
            [aoc.2019.intcode :refer [set-noun-verb run-prog]]
            [clojure.string :as str]))

(def input (mapv read-string (str/split (file-util/read-file "2019/d02.txt") #",")))

(defn part-1 [input] (-> input (set-noun-verb 12 2) run-prog first))

(defn part-2 [input]
  (let [magic-output 19690720]
    (first (for [noun  (range 100)
                 verb  (range 100)
                 :let  [mem (-> input (set-noun-verb noun verb) run-prog)]
                 :when (= magic-output (first mem))]
             (+ verb (* 100 noun))))))
