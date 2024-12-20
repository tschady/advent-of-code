(ns aoc.2024.d19
  (:require
   [aoc.file-util :as f]
   [clojure.string :as str]))

(def input (f/parse-chunks "2024/d19.txt"
                            [[:towels #(set (str/split % #", "))]
                             [:designs #(str/split-lines %)]]))

(def match-count*
  (memoize
   (fn [towels design]
     (if (empty? design)
       1
       (reduce + (for [towel towels
                       :when (str/starts-with? design towel)]
                   (match-count* towels (subs design (count towel)))))))))

(defn part-1 [{:keys [towels designs]}]
  (count (filter #(pos? (match-count* towels %)) designs)))

(defn part-2 [{:keys [towels designs]}]
  (transduce (map (partial match-count* towels)) + designs))
