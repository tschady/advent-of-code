(ns aoc.2019.d01
  (:require [aoc.file-util :as file-util]
            [clojure.core.reducers :as r]))

(def input (file-util/read-ints "2019/d01.txt"))

(defn mass->fuel
  "Return the fuel units required to launch a module of given `mass`,
  not including the mass of the fuel carried."
  [mass]
  (-> mass (quot 3) (- 2) (max 0)))

(defn total-fuel
  "Return the fuel units required to launch a module of given `mass`,
  including the mass of the fuel carried."
  [mass]
  (->> mass
       (iterate mass->fuel)
       rest
       (take-while pos?)
       (reduce +)))

(defn part-1
  [input]
  (->> input
       (r/map mass->fuel)
       (r/fold +)))

(defn part-2
  [input]
  (->> input
       (r/map total-fuel)
       (r/fold +)))
