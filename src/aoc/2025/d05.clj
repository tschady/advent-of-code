(ns aoc.2025.d05
  (:require
   [aoc.file-util :as f]
   [aoc.string-util :as s]
   [clojure.string :as str]))

(def input (f/read-chunks "2025/d05.txt"))

(defn fresh? [id [a b]] (<= a id b))

(defn part-1 [input]
  (let [rngs (map s/ints-pos (str/split-lines (first input)))
        ids (s/ints (second input))]
    (count (keep #(some (partial fresh? %) rngs) ids))))

(defn overlap? [[lo1 hi1] [lo2 hi2]]
  (or (<= lo1 lo2 hi1)
      (<= lo1 hi2 hi1)))

(defn merge-overlap [[lo1 hi1] [lo2 hi2]]
  [(min lo1 lo2) (max hi1 hi2)])

(defn merge-ranges [ranges r2]
  (let [r1 (peek ranges)]
    (if (overlap? r1 r2)
      (conj (pop ranges) (merge-overlap r1 r2))
      (conj ranges r2))))

(defn part-2 [input]
  (let [rngs (map s/ints-pos (str/split-lines (first input)))]
    (->> (rest rngs)
         (sort-by (juxt first second))
         (reduce merge-ranges [(first rngs)])
         (map #(inc (- (second %) (first %))))
         (reduce +))))
