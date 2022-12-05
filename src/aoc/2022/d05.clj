(ns aoc.2022.d05
  (:require
   [aoc.file-util :as f]
   [aoc.string-util :as s]
   [clojure.string :as str]))

(def input (f/read-chunks "2022/d05.txt"))

(defn parse [input]
  (let [stacks (->> (first input)
                    str/split-lines
                    butlast
                    (map rest)
                    (map (partial take-nth 4))
                    (apply map list)
                    (mapv (partial drop-while #{\space})))
        moves  (->> (second input)
                    str/split-lines
                    (map s/ints))]
    [stacks moves]))

(defn move [op stacks [n from to]]
  (-> stacks
      (update (dec from) #(drop n %))
      (update (dec to) #(reduce conj % (->> (get stacks (dec from))
                                            (take n)
                                            op)))))

(defn solve [op input]
  (let [[stacks moves] (parse input)]
    (->> (reduce (partial move op) stacks moves)
         (map first)
         (apply str))))

(defn part-1 [input] (solve identity input))

(defn part-2 [input] (solve reverse input))
