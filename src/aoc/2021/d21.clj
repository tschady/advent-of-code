(ns aoc.2021.d21
  (:require
   [aoc.file-util :as file-util]
   [aoc.math-util :refer [mod-1]]
   [medley.core :refer [find-first]]))

(def input (->> (file-util/read-file "2021/d21.txt")
                (re-seq #"\d+")
                (drop 1)
                (take-nth 2)
                (mapv read-string)))

;; NOTES part-1 easy, not sure to use iterate or a loop, guessing for part2.  iterate
;; was easier to debug.  I started with cycles for infinite die roll and next player
;;
;; i.e.  `(def fake-d100 (cycle (range 1 101)))`
;;
;; but changed them out to functions for easier debugging.

;; part-2 Fun.  I notice we no longer need turn, and the die is the same every round.
;; I should probably memoize, but I like having iterate for every possible
;; state in case i want to visualize (later!)

(defn make-game [die-fn pos]
  {:turn   0
   :scores [0 0]
   :pos    pos
   :player 0
   :die-fn die-fn})

(defn deterministic-die [turn] (+ 3 (* 3 (inc (* 3 turn)))))

(defn advance-pos [roll pos] (mod-1 (+ roll pos) 10))

(defn advance-state [{:keys [pos scores player] :as state} roll]
  (let [new-pos (advance-pos roll (get pos player))]
    (-> state
        (update-in [:scores player] + new-pos)
        (assoc-in [:pos player] new-pos)
        (assoc :player (mod (inc player) 2)))))

(defn play-turn [{:keys [turn pos player die-fn] :as game}]
  (-> game
      (advance-state (die-fn turn))
      (update :turn inc)))

(defn winner [n {:keys [scores]}]
  (first (keep-indexed #(when (<= n %2) %) scores)))

(defn final-score [{:keys [turn scores]}]
  (* 3 turn (reduce min scores)))

(defn part-1 [input]
  (->> input
       (make-game deterministic-die)
       (iterate play-turn)
       (find-first (partial winner 1000))
       final-score))

(def dirac-rolls
  "The possible outcomes by frequency of 3d3."
  (frequencies (for [r1 [1 2 3]
                     r2 [1 2 3]
                     r3 [1 2 3]]
                 (+ r1 r2 r3))))

(defn init-dirac [pos] {:pos pos :scores [0 0] :player 0})

(defn dirac-poss [state n]
  (reduce (fn [state-hash [roll freq]]
            (merge-with + state-hash (hash-map (advance-state state roll) (* n freq))))
          {}
          dirac-rolls))

(defn play-dirac [win-score input]
  (loop [world {:winners    [0 0]
                :state-hash {(init-dirac input) 1}}]
    (if (empty? (:state-hash world))
      (:winners world)
      (recur
       (reduce (fn [world [state n]]
                 (let [poss                   (dirac-poss state n)
                       {p1 0 p2 1 remain nil} (group-by #(winner win-score (key %)) poss)]
                   (-> world
                       (update-in [:winners 0] + (reduce + (map second p1)))
                       (update-in [:winners 1] + (reduce + (map second p2)))
                       (update :state-hash dissoc state)
                       (update :state-hash #(merge-with + % (into {} remain))))))
               world
               (:state-hash world))))))

(defn part-2 [input]
  (apply max (play-dirac 21 input)))
