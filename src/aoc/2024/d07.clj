(ns aoc.2024.d07
  (:require
   [aoc.file-util :as f]))

(def input (f/read-int-vectors "2024/d07.txt"))

(defn || [a b] (parse-long (str a b)))

(defn valid? [ops [result & operands]]
  (some #{result}
        (reduce (fn [poss n] (mapcat #(map (fn [op] (op % n)) ops) poss))
                (list (first operands))
                (rest operands))))

(defn solve [ops input]
  (reduce + (map first (filter (partial valid? ops) input))))

(defn part-1 [input] (solve [+ *] input))

(defn part-2 [input] (solve [+ * ||] input))
