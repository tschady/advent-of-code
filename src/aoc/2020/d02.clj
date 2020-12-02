(ns aoc.2020.d02
  (:require [aoc.file-util :as file-util]
            [aoc.math-util :as math-util]))

(def input (file-util/read-lines "2020/d02.txt"))

(defn parse-line [s]
  (let [[_ min max c pwd] (re-find #"^(\d+)-(\d+) ([a-z]): (\w+)$" s)]
    [(first c) (read-string min) (read-string max) pwd]))

(defn valid? [[c min max pwd]]
  (let [freq (get (frequencies pwd) c 0)]
    (<= min freq max)))

(defn valid2? [[c p1 p2 pwd]]
  (let [c1 (nth pwd (dec p1))
        c2 (nth pwd (dec p2))]
    (math-util/xor (= c c1) (= c c2))))

(defn solve [input rule] (count (filter rule (map parse-line input))))

(defn part-1 [input] (solve input valid?))

(defn part-2 [input] (solve input valid2?))
