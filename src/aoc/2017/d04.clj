(ns aoc.2017.d04
  (:require [aoc.util :as util]
            [clojure.string :as str]))

(def input (util/read-lines "2017/d04.txt"))

(defn valid-phrase?
  "Boolean to determine if a particular passphrase is valid.
  A valid passphrase does not have any duplicates relative to `strategy`."
  [strategy s]
  (->> (str/split s #" ")
       (map strategy)
       (apply distinct?)))

(defn count-valids
  "Return number of valid phrases of `input` collection based on `strategy`."
  [strategy input]
  (->> input
       (filter (partial valid-phrase? strategy))
       count))

(defn part-1
  [input]
  (count-valids identity input))

(defn part-2
  [input]
  (count-valids util/alphagram input))
