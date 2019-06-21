(ns aoc.2016.d02
  (:require [aoc.file-util :as file-util]))

(def input (file-util/read-lines "2016/d02.txt"))

(def dir->step {\U [0 -1]
                \D [0 1]
                \L [-1 0]
                \R [1 0]})

(def keypad1 {[0 0] "1", [1 0] "2", [2 0] "3"
              [0 1] "4", [1 1] "5", [2 1] "6"
              [0 2] "7", [1 2] "8", [2 2] "9"})

(def keypad2 {                      [2 0] "1"
                         [1 1] "2", [2 1] "3", [3 1] "4",
              [0 2] "5", [1 2] "6", [2 2] "7", [3 2] "8", [4 2] "9"
                         [1 3] "A", [2 3] "B", [3 3] "C"
                                    [2 4] "D"})

(defn solve [input start keypad]
  (letfn [(in-bounds? [loc] (contains? keypad loc))
          (move [loc step]
            (let [new-loc (mapv + loc step)]
              (if (in-bounds? new-loc) new-loc loc)))
          (dirs->loc [pos dirs] (reduce move pos (map dir->step dirs)))]
    (->> input
         (reductions dirs->loc start)
         rest ; we don't want the starting position
         (map keypad)
         (apply str))))

(defn part-1 [input] (solve input [1 1] keypad1))
(defn part-2 [input] (solve input [0 2] keypad2))
