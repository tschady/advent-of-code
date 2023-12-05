(ns aoc.2023.d01
  (:require
   [aoc.file-util :as f]
   [clojure.string :as str]))

(def input (f/read-lines "2023/d01.txt"))

(def word-hack {"one"   "o1e"
                "two"   "t2o"
                "three" "t3e"
                "four"  "f4r"
                "five"  "f5e"
                "six"   "s6x"
                "seven" "s7n"
                "eight" "e8t"
                "nine"  "n9e"})

(defn calibrate [s]
  (->> (re-seq #"\d" s)
       (#(str (first %) (last %)))
       parse-long))

(defn munge [s] (reduce-kv #(str/replace %1 %2 %3) s word-hack))

(defn part-1 [input] (transduce (map calibrate) + input))

(defn part-2 [input] (transduce (comp (map munge) (map calibrate)) + input))
