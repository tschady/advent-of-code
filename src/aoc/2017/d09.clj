(ns aoc.2017.d09
  (:require [aoc.file-util :as file-util]
            [clojure.string :as str]
            [aoc.coll-util :as coll-util]))

(def input (file-util/read-file "2017/d09.txt"))

(defn clean
  "Remove ignored chars and 'garbage' from string `s`"
  [s]
  (-> s
      (str/replace #"!." "")
      (str/replace #"<.*?>" "")))

(defn normalize
  "Change map literal braces to list parens so we can just read-string and walk it."
  [s]
  (apply str (replace {\{ \(, \} \)} s)))

(defn part-1 [input]
  (->> input
       clean
       normalize
       read-string
       (coll-util/tree-seq-depth list? identity)
       (map first)
       (map inc)
       (reduce +)))

(defn part-2 [input]
  (->> (str/replace input #"!." "")
       (re-seq #"<.*?>")
       (map count)
       (map #(- % 2))
       (reduce +)))
