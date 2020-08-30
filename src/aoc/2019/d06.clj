(ns aoc.2019.d06
  (:require [aoc.file-util :as file-util]
            [clojure.set :as set]
            [clojure.string :as str]))

(def input (file-util/read-lines "2019/d06.txt"))

(defn parse-rel [s] (mapv keyword (str/split s #"\)")))

(defn make-hier [xs]
  (reduce (fn [h [parent child]] (derive h child parent))
          (make-hierarchy)
          xs))

(defn part-1 [input]
  (let [orbits (make-hier (map parse-rel input))]
    (reduce + (map count (vals (:descendants orbits))))))

(defn part-2 [input]
  (let [orbits (make-hier (map parse-rel input))
        ys (ancestors orbits :YOU)
        ss (ancestors orbits :SAN)]
    (count (set/difference (set/union ys ss) (set/intersection ys ss)))))
