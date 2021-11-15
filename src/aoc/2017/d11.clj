(ns aoc.2017.d11
  (:require [aoc.file-util :as file-util]
            [aoc.hex :as hex]))

(def input (first (file-util/read-csv "2017/d11.txt")))

(defn part-1 [input] (hex/distance (hex/walk :flat-top input)))

(defn part-2 [input] (->> input
                          (reductions (partial hex/step :flat-top) [0 0 0])
                          (map hex/distance)
                          (apply max)))
