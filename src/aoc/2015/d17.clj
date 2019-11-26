(ns aoc.2015.d17
  (:require [aoc.file-util :as file-util]))

(def input (file-util/read-values "2015/d17.txt"))

(defn- change-combos
  "Return all possible combinations from the set of `denoms` that exactly
  sum to `amt`."
  ([amt denoms] (change-combos amt denoms [[]]))
  ([amt denoms acc]
   (cond (zero? amt) acc
         (or (neg? amt) (empty? denoms)) (pop acc)
         :else (concat (change-combos amt (rest denoms) acc)
                       (change-combos (- amt (first denoms))
                                      (rest denoms)
                                      (update acc (dec (count acc)) conj (first denoms)))))))

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
