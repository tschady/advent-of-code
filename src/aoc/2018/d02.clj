(ns aoc.2018.d02
  (:require [aoc.file-util :as file-util]
            [aoc.string-util :as string-util]
            [clojure.set :as set]))

(def input (file-util/read-lines "2018/d02.txt"))

(defn- count-words-with-dup-letters
  "Return the count of words which have exactly `n` repeated letters."
  [freq-map n]
  (->> freq-map
       (map #(filter (fn [[k v]] (= v n)) %))
       (remove empty?)
       count))

(defn part-1
  "Compute the checksum of `coll`, given by the product of
  the count of items which contain 1+ occurrence of exactly 2 repeated letters, and
  the count of items which contain 1+ occurrence of exactly 3 repeated letters."
  [coll]
  (let [freq-map (map frequencies coll)
        count-2 (count-words-with-dup-letters freq-map 2)
        count-3 (count-words-with-dup-letters freq-map 3)]
    (* count-2 count-3)))

(defn part-2
  "Determine the common letters between the first two items of `coll`
  which have a hamming-distance of 1."
  [coll]
  (first (keep identity (for [x coll
                              y coll]
                          (when (= 1 (string-util/hamming-distance x y))
                            (string-util/string-intersection x y))))))
