(ns aoc.2020.d06
  (:require [aoc.file-util :as file-util]
            [clojure.set :refer [intersection]]
            [clojure.string :as str]))

(def input (map str/split-lines (file-util/read-chunks "2020/d06.txt")))

(defn part-1 [input]
  (reduce + (map (comp count distinct (partial apply str)) input)))

(defn part-2 [input]
  (reduce + (map (comp count (partial apply intersection) (partial map set)) input)))
