(ns aoc.2022.d08
  (:require
   [aoc.file-util :as f]
   [aoc.matrix :as mat]
   [aoc.string-util :as s]
   [clojure.core.matrix :as m]
   [medley.core :refer [take-upto]]))

(def input (map s/explode-digits (f/read-lines "2022/d08.txt")))

(defn radial-vals
  "Return a tuple of all matrix `m` values radiating out from `[x y]`"
  [m [x y]]
  (let [[lefts [_ & rights]] (split-at x (m/get-row m y))
        [tops [_ & bottoms]] (split-at y (m/get-column m x))]
    [(reverse lefts) rights (reverse tops) bottoms]))

(defn visible? [m [x y :as loc]]
  (let [height (m/mget m y x)]
    (->> (radial-vals m loc)
         (map #(every? (fn [tree] (< tree height)) %))
         (some true?))))

(defn scenic-score [m [x y :as loc]]
  (let [height (m/mget m y x)]
    (->> (radial-vals m loc)
         (map #(count (take-upto (fn [tree] (>= tree height)) %)))
         (apply *))))

(defn part-1 [m]
  (->> (mat/coords m)
       (filter (partial visible? m))
       count))

(defn part-2 [m]
  (->> (mat/coords m)
       (map (partial scenic-score m))
       (apply max)))
