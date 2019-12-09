(ns aoc.2019.d05
  (:require [clojure.string :as str]
            [aoc.file-util :as file-util]
            [aoc.2019.intcode :as intcode]))

(def input (mapv read-string (str/split ( file-util/read-file "2019/d05.txt") #",")))

(:out (intcode/run-prog (intcode/make-prog input [1])))

(:out (intcode/run-prog (intcode/make-prog input [5])))


