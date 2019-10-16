(ns aoc.2015.d14
  (:require [aoc.coll-util :as coll-util]
            [aoc.file-util :as file-util]))

(def input (file-util/read-lines "2015/d14.txt"))

(defn parse-line [line] (mapv read-string (re-seq #"\d+" line)))

(defn dist-per-tick
  "Return a lazy seq of the non-cumulative distances traveled for each
  second, given a velocity, motion time, and time to rest for a reindeer."
  [[v t rests]]
  (cycle (concat (repeat t v) (repeat rests 0))))

(defn pos-per-tick [reindeer] (reductions + (dist-per-tick reindeer)))

(defn part-1
  "Return distance the winning reindeer has traveled after `t` seconds."
  [stats t]
  (->> (map parse-line stats)
       (map #(pos-per-tick %))
       (map #(nth % t))
       (apply max)))

(defn part-2
  "Return max number of seconds any reindeer was in the lead after `t` seconds."
  [stats t]
  (->> (map parse-line stats)
       (map #(pos-per-tick %))
       (map #(take t %))
       (apply map vector)
       (map coll-util/idx-of-max)
       (frequencies)
       (map second)
       (apply max)))
