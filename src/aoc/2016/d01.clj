(ns aoc.2016.d01
  (:require [aoc.file-util :as file-util]
            [clojure.string :as str]
            [aoc.math-util :as math-util]))

(def input (file-util/read-file "2016/d01.txt"))

(def bearings [[0 -1] [1 0] [0 1] [-1 0]])
(def dir->adjust-bearing {\R inc, \L dec})

(def origin {:dir 0, :loc [0 0]})

(defn parse-moves
  "Transform CSV moves into vector of moves, with each move as a tuple:
  the direction of turn (\\R or \\L) and the distance to move.
  e.g. 'R5, L2' => [[\\R 5] [\\L 2]]"
  [move-str]
  (->> (str/split move-str #", ")
       (map (juxt first #(Integer/parseInt (apply str (rest %)))))))

(defn turn
  [pos dir]
  (update pos :dir (comp #(mod % 4) (dir->adjust-bearing dir))))

(defn move
  [pos steps]
  (let [dist (mapv (partial * steps) (bearings (:dir pos)))]
    (update pos :loc (partial mapv + dist))))

(defn walk
  [start moves]
  (reduce (fn [pos [dir steps]] (-> pos
                                    (turn dir)
                                    (move steps)))
          start
          moves))

(defn part-1
  ""
  [input]
  (let [dest (walk origin (parse-moves input))]
    (math-util/manhattan-dist (:loc origin) (:loc dest))))
