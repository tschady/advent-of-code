(ns aoc.2015.d12
  (:require
   [aoc.file-util :as file-util]
   [aoc.string-util :as string-util]
   [clojure.data.json :as json]
   [clojure.walk :refer [prewalk]]))

(def input (file-util/read-file "2015/d12.txt"))

(defn part-1
  "Return the sum of all digits from the input string."
  [s]
  (reduce + 0 (string-util/ints s)))

(defn part-2
  "Return the sum of all digits from the input string, except those who
  appear in any object with any property set to value 'red'."
  [s]
  (->> s
       json/read-str
       (prewalk #(cond
                   (and (map? %) (some (partial = "red") (vals %))) nil
                   (map? %) (seq %)
                   :else %))
       flatten
       (filter integer?)
       (reduce + 0)))
