(ns aoc.2019.d05
  (:require [clojure.string :as str]
            [aoc.file-util :as file-util]
            [aoc.2019.intcode :as intcode]))

(def tape (mapv read-string (str/split ( file-util/read-file "2019/d05.txt") #",")))

(defn solve [tape input]
  (peek (:out (intcode/run-prog (intcode/make-prog tape input)))))

(defn part-1 [tape] (solve tape [1]))

(defn part-2 [tape] (solve tape [5]))
