(ns aoc.2020.d25
  (:require [aoc.file-util :as file-util]))

(def input (file-util/read-ints "2020/d25.txt"))

(defn xform-step [subj n] (rem (* subj n) 20201227))

(defn loop-size [public-key]
  (->> (iterate (partial xform-step 7) 1)
       (take-while #(not= % public-key))
       count))

(defn part-1 [input]
  (-> (iterate (partial xform-step (second input)) 1)
      (nth (loop-size (first input)))))
