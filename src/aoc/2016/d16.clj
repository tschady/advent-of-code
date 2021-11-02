(ns aoc.2016.d16
  (:require [aoc.file-util :as file-util]))

(def input (first (file-util/read-lines "2016/d16.txt")))

(def disk-p1 272)

(def disk-p2 35651584)

(defn dragon-grow [s]
  (str s "0" (apply str (replace {\1 \0 \0 \1} (reverse s)))))

(defn fill-disk [size init-state]
  (take size (first (drop-while #(< (count %) size) (iterate dragon-grow init-state)))))

(defn checksum [state]
  {:pre  [(even? (count state))]
   :post [(odd? (count %))]}
  (let [sum (map #(if (= (first %) (second %)) \1 \0) (partition 2 state))]
    (if (even? (count sum))
      (checksum sum)
      (apply str sum))))

(defn solve [size state] (checksum (fill-disk size state)))

;; part1:  (solve disk-p1 input)
;; part2:  (solve disk-p2 input)
