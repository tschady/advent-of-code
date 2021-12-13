(ns aoc.2021.d13
  (:require
   [aoc.file-util :as file-util]
   [aoc.grid :as grid]
   [aoc.string-util :as string-util]
   [com.rpl.specter :refer [ALL FIRST LAST pred> transform]]))

(def input (file-util/read-chunks "2021/d13.txt"))

(defn parse [[coords folds]]
  {:paper (partition 2 (string-util/ints coords))
   :folds (->> folds
               (re-seq #"([xy])=(\d+)")
               (map rest)
               (map #(map read-string %)))})

(defn fold [paper [axis v]]
  (set (transform [ALL (if (= 'x axis) FIRST LAST) (pred> v)] #(- (* 2 v) %) paper)))

(defn part-1 [input]
  (let [{:keys [paper folds]} (parse input)]
    (count (fold paper (first folds)))))

(defn part-2 [input]
  (let [{:keys [paper folds]} (parse input)]
    (-> (reduce fold paper folds)
        (zipmap (repeat \#))
        grid/print)))
