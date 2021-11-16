(ns aoc.2015.d17
  (:require [aoc.file-util :as file-util]
            [aoc.math-util :refer [change-combos]]))

(def input (file-util/read-ints "2015/d17.txt"))

(defn part-1
  "Return the total number of combinations of containers whose capacities
  are given by the set `denom` that exactly use up `amount` liters of water."
  [amount denoms]
  (count (change-combos amount denoms)))

(defn part-2
  "Return the number of container combinations that use the minimum number
  from the set of `denoms` that exactly use up `amount` liters of water."
  [amount denoms]
  (->> (change-combos amount denoms)
       (group-by count)
       (into (sorted-map))
       first
       val
       count))
