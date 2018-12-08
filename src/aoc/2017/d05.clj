(ns aoc.2017.d05
  (:require [aoc.file-util :as file-util]))

(def input (->> "2017/d05.txt" file-util/read-lines (mapv #(Integer/parseInt %))))

(defn steps-to-exit
  [tape update-fn]
  (loop [loc 0
         tape tape
         steps 1]
    (let [cur-val (get tape loc)
          new-loc (+ loc cur-val)
          new-tape (update tape loc update-fn)]
      (if (>= new-loc (count tape))
        steps
        (recur new-loc new-tape (inc steps))))))

(defn p2-offset-fn [n] (if (> n 2) (dec n) (inc n)))

(defn part-1 [input]
  (steps-to-exit input inc))

(defn part-2 [input]
  (steps-to-exit input p2-offset-fn))
