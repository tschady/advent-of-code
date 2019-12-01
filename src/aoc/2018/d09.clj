(ns aoc.2018.d09
  (:require [aoc.file-util :as file-util]))

(def input (file-util/read-file "2018/d09.txt"))

(defn- make-game
  "Create an initial game data structure from descriptive string."
  [game-str]
  (let [[num-players max-turns] (map read-string (re-seq #"\d+" game-str))]
    {:num-players num-players
     :max-turns max-turns
     :player 0
     :marble 0
     :board [0]
     :cur-loc 0
     :scores (vec (repeat (inc num-players) 0))}))

(defn- special-marble?
  "Predicate for magic scoring marbles which are multiples of 23."
  [n]
  (zero? (mod n 23)))

(defn- add-marble
  "Insert the specified `marble` into the `board` at index `loc`.
  Note: very slow vector operations."
  [loc board marble]
  (vec (concat (subvec board 0 loc) [marble] (subvec board loc))))

(defn- remove-marble
  "Remove the marble specified at point `loc` from the `board`.
  Note: very slow vector operations."
  [loc board]
  (vec (concat (subvec board 0 loc) (subvec board (inc loc)))))

(defn- get-next-loc
  "Given a board and current location, determine the next location to add
  the marble.  Next location is two places to the right on a looped board."
  [board cur-loc]
  (inc (mod (inc cur-loc) (count board))))

(defn- get-next-player
  "Return next player number.  Advances by one, wrapping around to start.
  Player indexes are 1-based."
  [player max-num]
  (let [next-player (inc player)]
    (if (<= next-player max-num) next-player 1)))

(defn- get-remove-loc
  "Special marbles trigger the removal of the marble 7 to the left of the current point on a looped board."
  [board cur-loc]
  (mod (- cur-loc 7) (count board)))

(defn play-turn
  "Advances game state by one turn."
  [game]
  (let [{:keys [num-players player marble board cur-loc scores]} game
        next-player (get-next-player player num-players)
        next-marble (inc marble)
        game (assoc game :player next-player :marble next-marble)]
    (if (special-marble? next-marble)
      (let [remove-loc (get-remove-loc board cur-loc)
            removed-val (get board remove-loc)]
        (assoc game
               :board (remove-marble remove-loc board)
               :cur-loc remove-loc
               :scores (update scores player + next-marble removed-val)))
      (let [next-loc (get-next-loc board cur-loc)]
        (assoc game
               :board (add-marble next-loc board next-marble)
               :cur-loc next-loc)))))

(defn play-game2
  [num-players max-turn]
  (loop [player-rotation (cycle (range num-players))
         marble       1
         cur-loc      0
         board (java.util.ArrayList. '(0))
         scores       (vec (repeat num-players 0))]
    (if (> marble max-turn)
      scores ;; terminate with score list
      (if (special-marble? marble)
        ;; remove item (update board), set location, and update scores
        ;; else add marble (update board), and advance location
        )
      (recur [(rest player-rotation)
              (inc marble)
              (update-loc cur-loc)
              (update-board board)
              (update-scores scores)]))))

(identity '(2 3))

(defn play-game
  "Iterate through game's specified number of turns, given starting conditions."
  [game]
  (nth (iterate play-turn game) (:max-turns game)))

(defn high-score
  "Find highest score amongst all players."
  [game]
  (apply max (:scores game)))

(defn part-1
  "Determine highest score for given game."
  [game-str]
  (-> game-str make-game play-game high-score))

(defn part-2
  "Determine highest score for given game, playing 100x the specified turns."
  [game-str]
  (-> game-str make-game (update :max-turns * 100) play-game high-score))
