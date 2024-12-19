(ns aoc.2024.d19
  (:require
   [aoc.file-util :as f]
   [clojure.string :as str]))

(def input (f/read-chunks "2024/d19.txt"))

(defn parse-towels [s] (set (str/split s #", ")))

(defn parse-designs [s] (str/split-lines s))

(def match-count*
  (memoize
   (fn [towels design]
     (if (empty? design)
       1
       (reduce + (for [towel towels
                       :when (str/starts-with? design towel)]
                   (match-count* towels (subs design (count towel)))))))))

(defn part-1 [input]
  (let [towels (parse-towels (first input))
        designs (parse-designs (second input))]
    (count (filter #(pos? (match-count* towels %)) designs))))

(defn part-2 [input]
  (let [towels (parse-towels (first input))
        designs (parse-designs (second input))]
    (transduce (map (partial match-count* towels)) + designs)))
