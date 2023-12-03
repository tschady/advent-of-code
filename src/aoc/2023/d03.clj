(ns aoc.2023.d03
  (:require
   [aoc.file-util :as f]
   [aoc.grid :as grid]
   [aoc.math-util :as math-util :refer [digits->num]]
   [plumbing.core :refer [for-map]]))

(def input (f/read-lines "2023/d03.txt"))

(defn- token [c]
  (cond
    (java.lang.Character/isDigit c) :digit
    (= \. c) :empty
    :else :symbol))

(defn- parse-line [acc group]
  (let [size (count group)
        x    (:x acc)
        y    (:y acc)
        acc' (update acc :y + size)]
   (case (token (first group))
     :digit
     (let [idx  (count (get-in acc [:schematic :nums]))
           locs (for-map [dy (range size)] [x (+ y dy)] idx)]
       (-> acc'
           (update-in [:schematic :nums] conj (digits->num group))
           (update-in [:schematic :grid] merge locs)))

     :symbol
     ;; FIXME: assumes no co-adjacent symbols
     (update-in acc' [:schematic :symbols (first group)] conj [x y])

     :empty acc')))

(defn build-schematic
  "Return a map with the following entries
  `:symbols` :map of symbol to vector of grid locations where found
  `:nums`: an ordered vector of part-numbers found in the schematic
  `:grid` coords containing a number mapped to the index into `nums`
          for its value"
  [data]
  (:schematic
   (reduce (fn [acc line]
             (let [groups (partition-by token line)]
               (-> (reduce parse-line acc groups)
                   (update :x inc) ; advance row
                   (assoc :y 0)))) ; reset col
           {:x 0 :y 0 :schematic {:nums [] :grid {} :symbols {}}}
           data)))

(defn adj-nums [schematic loc]
  (->> (grid/neighbor-coords loc)
       (keep (:grid schematic))
       distinct
       (map #(get (:nums schematic) %))))

(defn part-1 [input]
  (let [schematic (build-schematic input)]
    (->> schematic
         :symbols
         vals
         (apply concat)
         (mapcat (partial adj-nums schematic))
         (reduce +))))

(defn part-2 [input]
  (let [schematic (build-schematic input)]
    (->> (get-in schematic [:symbols \*])
         (map (partial adj-nums schematic))
         (filter #(= 2 (count %)))
         (map (partial reduce *))
         (reduce +))))
