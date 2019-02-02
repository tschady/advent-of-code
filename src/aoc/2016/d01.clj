(ns aoc.2016.d01
  (:require [aoc.file-util :as file-util]
            [clojure.string :as str]
            [aoc.math-util :as math-util]))

(def input (file-util/read-file "2016/d01.txt"))

(def bearings [[0 -1] [1 0] [0 1] [-1 0]])
(def dir->adjust-bearing {\R inc, \L dec})

(def origin [0 0])

(defn parse-moves
  "Transform CSV moves into vector of moves, with each move as a tuple:
  the direction of turn (\\R or \\L) and the distance to move.
  e.g. 'R5, L2' => [[\\R 5] [\\L 2]]"
  [move-str]
  (->> (str/split move-str #", ")
       (map (juxt first #(Integer/parseInt (apply str (rest %)))))))

(defn steps
  "Determine series of cartesian deltas from list of moves."
  [moves]
  (let [turns (map first moves)
        dists (map second moves)
        indexes (->> turns
                     (reductions #(mod ((dir->adjust-bearing %2) %1) 4) 0)
                     rest)
        deltas (map bearings indexes)]
    (mapcat #(repeat %1 %2) dists deltas)))

(defn walk
  "Return every location reached by applying steps given by deltas."
  [deltas from]
  (reductions (partial mapv +) from deltas))

(defn part-1
  "Return Manhattan distance from origin determined by walking input."
  [input]
  (let [dest (-> input parse-moves steps (walk origin) last)]
    (math-util/manhattan-dist origin dest)))

(defn part-2
  "Return Manhattan distance from origin of first location reached twice
  by walking given input."
  [input]
  (let [locs (-> input parse-moves steps (walk origin))
        dup (first (math-util/first-duplicate locs))]
    (math-util/manhattan-dist origin dup)))
