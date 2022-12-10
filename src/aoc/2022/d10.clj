(ns aoc.2022.d10
  (:require
   [aoc.elfscript :as elfscript]
   [aoc.file-util :as f]
   [aoc.string-util :as s]
   [clojure.string :as str]))

(def input (f/read-file "2022/d10.txt"))

(defn reg-series [input]
  (let [deltas (-> input (str/replace #"noop|addx" "0") s/ints)]
    (reductions + 1 deltas)))

(defn part-1 [input]
  (->> [20 60 100 140 180 220]
       (map #(* % (nth (reg-series input) (dec %))))
       (reduce +)))

(defn draw-pixel [cursor cycle]
  (let [dist (abs (- (mod cursor 40) cycle))]
    (if (<= dist 1) \# \.)))

(defn part-2 [input]
  (->> (reg-series input)
       (map-indexed draw-pixel)
       (partition 40)
       (map (partial apply str))
       (elfscript/ocr)))
