(ns aoc.2015.d24
  (:require [aoc.file-util :as file-util]
            [clojure.math.combinatorics :as combo]))

(def input (file-util/read-ints "2015/d24.txt"))

(defn qe-score [pkgs] (reduce * 1 pkgs))

(defn find-target-pkg-group-score [pkgs groups]
  (let [target-weight (/ (reduce + pkgs) groups)
        max-group-size (Math/floor (/ (count pkgs) groups))]
    (reduce (fn [res n]
              (if (seq res)
                (reduced (apply min (map qe-score res)))
                (->> (combo/combinations pkgs n)
                     (filter #(= target-weight (reduce + %)))
                     (into res))))
            '()
            (range 1 max-group-size))))

(defn part-1 [input] (find-target-pkg-group-score input 3))

(defn part-2 [input] (find-target-pkg-group-score input 4))
