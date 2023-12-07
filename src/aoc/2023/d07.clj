(ns aoc.2023.d07
  (:require
   [aoc.file-util :as f]
   [clojure.string :as str]))

(def input (f/read-lines "2023/d07.txt"))

(def card-val {\2 2, \3 3, \4 4, \5 5, \6 6, \7 7, \8 8, \9 9
               \T 10, \J 11, \Q 12, \K 13, \A 14})

(def card-val-joker (assoc card-val \J 1))

(def hand-type {'(5)         7
                '(4 1)       6
                '(3 2)       5
                '(3 1 1)     4
                '(2 2 1)     3
                '(2 1 1 1)   2
                '(1 1 1 1 1) 1})

(defn parse-hand [s]
  (let [[h v] (str/split s #" ")]
    [h (parse-long v)]))

(defn hand-rank [[hand _]]
  (hand-type (sort > (vals (frequencies hand)))))

(defn hand-rank-joker [[hand _]]
  (let [f (frequencies hand)
        jokers (get f \J 0)
        counts (sort > (vals (dissoc f \J)))
        wildcard-counts (cons ((fnil + 0) (first counts) jokers)
                              (rest counts))]
    (hand-type wildcard-counts)))

(defn hi-card [ranker [hand _]] (mapv ranker hand))

(defn solve [rank-fn val-fn input]
  (->> (map parse-hand input)
       (sort-by (juxt rank-fn (partial hi-card val-fn)))
       (map second)
       (map-indexed #(* (inc %1) %2))
       (reduce +)))

(defn part-1 [input]
  (solve hand-rank card-val input))

(defn part-2 [input]
  (solve hand-rank-joker card-val-joker input))
