(ns aoc.2016.d19
  (:require [aoc.file-util :as file-util]
            [clojure.data.finger-tree :as ft]
            [medley.core :refer [find-first]]))

(def input (first (file-util/read-ints "2016/d19.txt")))

(defn make-tree [n] (apply ft/counted-double-list (range 1 (inc n))))

(defn make-list [n] (vec (range 1 (inc n))))

(defn steal-across [ring]
  (let [thief   (first ring)
        mark    (int (/ (count ring) 2))
        [l _ r] (ft/ft-split-at ring mark)]
    (conj (rest (ft/ft-concat l r)) thief)))

(defn steal-next-round [ring]
  (cond->> ring
    true (take-nth 2)
    (odd? (count ring)) (drop 1)))

(defn solve [ring strategy]
  (->> (iterate strategy ring)
       (find-first #(= 1 (count %)))
       first))

(defn part-1 [input] (solve (make-list input) steal-next-round))

(defn part-2 [input] (solve (make-tree input) steal-across))
