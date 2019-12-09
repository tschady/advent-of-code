(ns aoc.2019.d09
  (:require [aoc.2019.intcode :as intcode]
            [aoc.file-util :as file-util]
            [clojure.string :as str]))

(def input (mapv read-string (str/split ( file-util/read-file "2019/d09.txt") #",")))

(:out (intcode/run-prog (intcode/make-prog input [1])))

#_(:out (intcode/run-prog (intcode/make-prog input [5])))
