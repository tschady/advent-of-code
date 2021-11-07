(ns aoc.2020.d23
  (:require [aoc.string-util :as string-util]
            [aoc.file-util :as file-util]))

(def input (vec (string-util/explode-digits (file-util/read-file "2020/d23.txt"))))

(defn find-dest
  [n max-n snippets]
  (let [target (dec n)]
    (cond
      (contains? snippets target) (recur target max-n snippets)
      (>= 0 target) (recur max-n max-n snippets)
      :else target)))

(defn make-ring
  "Return a mutable integer array (for performance reasons) with each index representing
  a cup's label, and the value being the next cup in line, resembling a hashmap of linked
  list nodes. Index 0 points to the 'current' cup."
  [input size]
  (let [ring (int-array (inc size))]
    (->> [[0], input, (range 10 (inc size)), (vector (first input))]
         (into [] cat)
         (partition 2 1)
         (map #(aset-int ring (first %) (second %)))
         dorun)
    ring))

(defn play [input size turns]
  (let [^ints ring (make-ring input size)]
    (dotimes [_ turns]
      (let [curr  (aget ring 0)
            snip1 (aget ring curr)
            snip2 (aget ring snip1)
            snip3 (aget ring snip2)
            curr2 (aget ring snip3)
            dest  (find-dest curr (inc size) #{snip1 snip2 snip3})
            dest2 (aget ring dest)]
        (aset ring curr curr2)
        (aset ring dest snip1)
        (aset ring snip3 dest2)
        (aset ring 0 curr2)))
    ring))

(defn get-nexts
  "Return a collection of the next `n` nodes after `start`"
  [ring start n]
  (->> start
       (iterate #(aget ^ints ring %))
       (drop 1)
       (take n)))

(defn part-1 [input]
  (apply str (get-nexts (play input 9 100) 1 8)))

(defn part-2 [input]
  (reduce * (get-nexts (play input 1e6 1e7) 1 2)))
