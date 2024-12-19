(ns aoc.2024.d18
  (:require
   [aoc.file-util :as f]
   [aoc.grid :as grid]
   [clojure.string :as str]
   [plumbing.core :refer [for-map]]
   [ubergraph.alg :as alg]
   [ubergraph.core :as uber]))

(def input (f/read-int-vectors "2024/d18.txt"))

(defn make-maze [walls [dx dy]]
  (merge (for-map [x (range (inc dx))
                   y (range (inc dy))]
                  [x y] \.)
         (zipmap walls (repeat \#))))

(defn solve [input size blocks]
  (let [g (make-maze (take blocks input) size)]
    (-> (grid/connected-adjacency-map #(= \. (get g %1) (get g %2))
                                       grid/neighbor-coords-news
                                       [0 0])
        uber/graph
        (alg/shortest-path [0 0] size)
        :cost)))

(defn part-1 [input] (solve input [70 70] 1024))

(defn part-2 [input]
  (->> (range (count input) 0 -1)
       (map #(solve input [70 70] %))
       (take-while #(nil? %))
       count
       (- (count input))
       (nth input)
       (str/join ",")))
