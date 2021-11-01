(ns aoc.2016.d08
  (:require [aoc.file-util :as file-util]
            [aoc.matrix :as matrix]))

(def input (file-util/read-lines "2016/d08.txt"))

(def init-panel (matrix/initialize 50 6 \.))

(defn line->command [panel line]
  (condp re-matches line
    #"rect (\d+)x(\d+)" :>>
    (fn [[_ x y]] (matrix/set-rect panel [0 0] (Long/parseLong x) (Long/parseLong y) \#))

    #"rotate row y=(\d+) by (\d+)" :>>
    (fn [[_ r n]] (matrix/rot-row-right panel (Long/parseLong r) (Long/parseLong n)))

    #"rotate column x=(\d+) by (\d+)" :>>
    (fn [[_ c n]] (matrix/rot-col-down panel (Long/parseLong c) (Long/parseLong n)))))

(defn final-panel [input] (reduce line->command init-panel input))

(defn part-1 [input]
  (-> (apply concat (final-panel input))
      frequencies
      (get \#)))

(defn part-2 [input]
  (matrix/pprint (final-panel input)))

(part-2 input) ; "ZJHRKCPLYJ"
