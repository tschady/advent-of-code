(ns aoc.2015.d04
  (:require [clojure.string :as str]
            [pandect.algo.md5 :refer [md5]]))

(def input "iwrupvqb")

(defn- solve
  "Return first positive integer that when added after 'secret',
  the resulting md5 hash begins with the target string."
  [secret target]
  (first (for [n (range)
               :let [candidate (str secret n)
                     digest (md5 candidate)]
               :when (str/starts-with? digest target)]
           n)))

(defn part-1 [secret] (solve secret "00000"))

(defn part-2 [secret] (solve secret "000000"))
