(ns aoc.grid
  (:require [clojure.string :as str]))

(defn parse-moves
  "Transform CSV moves into vector of moves, with each move as a tuple:
  the direction of turn (\\R or \\L) and the distance to move.
  e.g. 'R5, L2' => [[\\R 5] [\\L 2]]"
  [move-str]
  (let [moves (-> move-str
                  (str/replace #" " "")
                  (str/split #","))]
    (map (juxt first #(Integer/parseInt (str/join (rest %)))) moves)))

(def bearings [[0 -1] [1 0] [0 1] [-1 0]])
(def dir->adjust-bearing {\R inc, \L dec})

(def dir->delta {\U [0 1]
                 \D [0 -1]
                 \L [-1 0]
                 \R [1 0]})

(def origin [0 0])

(defn step-turns
  "Determine series of cartesian deltas from list of moves, where each
  move is given by it's relative turn ('R' or 'L'), and magnitude."
  [moves]
  (let [turns (map first moves)
        dists (map second moves)
        indexes (->> turns
                     (reductions #(mod ((dir->adjust-bearing %2) %1) 4) 0)
                     rest)
        deltas (map bearings indexes)]
    (mapcat repeat dists deltas)))

(defn step-moves
  "Determine series of cartesian deltas from list of moves, where each
  move is given by it's direction (U,D,L,R), and magnitude."
  [moves]
  (mapcat (fn [[dir magnitude]] (repeat magnitude (dir->delta dir))) moves))

(defn walk
  "Return every location reached by applying steps given by deltas."
  [deltas from]
  (reductions (partial mapv +) from deltas))

(defn turns->path
  [step-instructions]
  (-> step-instructions parse-moves step-turns (walk origin)))

(defn moves->path
  [move-instructions]
  (-> move-instructions parse-moves step-moves (walk origin)))
