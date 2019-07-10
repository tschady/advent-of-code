(ns aoc.2016.d05
  (:require [clojure.string :as str]
            digest
            [medley.core :refer [distinct-by]]))

(digest/md5 "foo")

(def input "wtnhxymk")

(defn target-hashes [input]
  (->> (range)
       (map (partial str input))
       (map digest/md5)
       (filter #(str/starts-with? % "00000"))))

(defn part-1
  ""
  [input]
  (->> (target-hashes input)
       (take 8)
       (map #(nth % 5))
       (apply str)))

(defn part-2
  ""
  [input]
  (->> (target-hashes input)
       (map (juxt (comp #(Character/digit % 10) #(nth % 5))
                  #(nth % 6)))
       (filter #(>= 8 (first %) 0))
       (distinct-by first)
       (take 8)
       (sort-by first)
       (map second)
       (apply str)))

;; (time (part-1 input))
;; "2414bc77"
;; 45.3s

;; (part-2 input)
;; "437e60fc"
;; 133.9s
