(ns aoc.2025.d02
    (:require
     [aoc.file-util :as f]))

(def input (partition 2 (first (f/read-ranges "2025/d02.txt"))))

(defn invalid-ids [re [a b]] (filter #(re-find re (str %)) (range a (inc b))))

(defn solve [input re] (apply + (mapcat (partial invalid-ids re) input)))

(defn part-1 [input] (solve input #"^(.+)\1$"))

(defn part-2 [input] (solve input #"^(.+)\1+$"))
