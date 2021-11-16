(ns aoc.2017.d06
  (:require [aoc.coll-util :refer [first-duplicate]]
            [aoc.file-util :as file-util]))

(def input (file-util/read-ints "2017/d06.txt"))

(defn realloc-blocks
  "Return next cycle of balancing memory blocks, by redistributing the
  blocks from the max bank, one-by-one, in order of index after the
  maximum bank, wrapping around the memory vector."
  [memory]
  (let [blocks (apply max memory)
        max-bank (.indexOf memory blocks)
        inc-banks (->> (range (count memory))
                       cycle
                       (drop (inc max-bank))
                       (take blocks))
        cleared-memory (assoc memory max-bank 0)]
    (reduce #(update % %2 inc) cleared-memory inc-banks)))

(defn part-1 [memory] (->> memory
                           (iterate realloc-blocks)
                           first-duplicate
                           second
                           second))

(defn part-2 [memory] (->> memory
                           (iterate realloc-blocks)
                           first-duplicate
                           second
                           (#(- (second %) (first %)))))
