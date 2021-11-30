(ns aoc.2017.d10
  (:require [aoc.file-util :as file-util]
            [aoc.string-util :as string-util]))

(def input (file-util/read-file "2017/d10.txt"))

(defn half-twist
  "Reverse the segment of data starting at position `start` with length `length`.
  Wrap this around to the start of the list if we go past the last element."
  [data start length]
  (let [indexes (mapv #(mod % (count data)) (range start (+ start length)))
        values (map #(get data %) indexes)
        revved (reverse values)]
    (reduce (fn [acc [k v]] (assoc acc k v))
            data
            (zipmap indexes revved))))

(defn sparse-hash [length-seq]
  (loop [ptr       0
         skip      0
         [i & ixs] length-seq
         data      (vec (range 256))]
    (if (nil? i)
      data
      (recur (+ ptr skip i)
             (inc skip)
             ixs
             (half-twist data ptr i)))))

(defn part-1 [input]
  (let [knot (sparse-hash (string-util/ints input))]
    (* (first knot) (second knot))))

(def extra [17, 31, 73, 47, 23])

(defn part-2 [input]
  (let [len-seq (vec (apply concat (repeat 64 (concat (map int input) extra))))
        sparse  (sparse-hash len-seq)
        dense   (->> sparse
                   (partition 16)
                   (map (partial apply bit-xor)))]
    (apply str (map (partial format "%02x" ) dense))))
