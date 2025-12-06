(ns aoc.2025.d06
  (:require
   [aoc.file-util :as f]
   [aoc.string-util :as s]
   [clojure.string :as str]))

(def input (f/read-lines "2025/d06.txt"))

(defn parse-ops [input]
  (map (comp resolve symbol) (str/split (last input) #"\s+")))

(defn parse1 [input]
  (let [args (map s/ints (butlast input))]
    (apply map list (conj args (parse-ops input)))))

(defn parse2 [input]
  (let [args (->> (butlast input)
                  (map seq)
                  (apply map list)
                  (map (partial apply str))
                  (partition-by #(every? #{\space} %))
                  (remove #(every? #{\space} (first %)))
                  (map #(map read-string %)))]
    (map conj args (parse-ops input))))

(defn part-1 [input] (reduce + (map eval (parse1 input))))

(defn part-2 [input] (reduce + (map eval (parse2 input))))
