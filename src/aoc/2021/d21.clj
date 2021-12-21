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

;; ^:blog
;; In line with my goal of being able to easily produce visualizations,
;; I avoid recursion (and thus forego memoization benefits) to make
;; lazy update functions usable with `iterate`.
;;
;; It wouldn't be practical to generalize the two halves of problem
;; since we don't need any concept of turn in part 2.
;; 
;; I started with cycles for infinite die roll and next player (i.e. `(def fake-d100 (cycle (range 1 101)))` but changed them out to functions for easier debugging.

(defn make-game [die-fn pos]
  {:turn   0
   :scores [0 0]
   :pos    pos
   :player 0
   :die-fn die-fn})

(defn ^:blog deterministic-die [turn] (+ 3 (* 3 (inc (* 3 turn)))))

;; ^:blog I used my new `mod-1` function again.

(defn ^:blog advance-pos [roll pos] (mod-1 (+ roll pos) 10))

;; ^:blog The active player is toggled, and used as the index of item to update

(defn ^:blog advance-state [{:keys [pos scores player] :as state} roll]
  (let [new-pos (advance-pos roll (get pos player))]
    (-> state
        (update-in [:scores player] + new-pos)
        (assoc-in [:pos player] new-pos)
        (assoc :player (mod (inc player) 2)))))

(defn ^:blog play-turn [{:keys [turn pos player die-fn] :as game}]
  (-> game
      (advance-state (die-fn turn))
      (update :turn inc)))

(defn winner [n {:keys [scores]}]
  (first (keep-indexed #(when (<= n %2) %) scores)))

(defn final-score [{:keys [turn scores]}]
  (* 3 turn (reduce min scores)))

;; ^:blog I like the readability of using `medley.core/find-first` to stop iteration.

(defn ^:blog part-1 [input]
  (->> input
       (make-game deterministic-die)
       (iterate play-turn)
       (find-first (partial winner 1000))
       final-score))

;; ^:blog Part 2 uses same iterative approach, which is much slower.  ~1.2s.
;; But this gives us the full state at every time tick in a lazy sequence
;; for viz.

(def ^:blog dirac-rolls
  "The possible outcomes by frequency of 3d3."
  (frequencies (for [r1 [1 2 3]
                     r2 [1 2 3]
                     r3 [1 2 3]]
                 (+ r1 r2 r3))))

(defn ^:blog dirac-poss
  "Return a map of the possible outcome states with their frequency, based on
  every possibility of a 3d3 roll."
  [state n]
  (reduce (fn [state-hash [roll freq]]
            (merge-with + state-hash (hash-map (advance-state state roll) (* n freq))))
          {}
          dirac-rolls))

(defn ^:blog step-dirac
  "Advance the state of the dirac world by 1 time-tick.  This updates all of our
  current states into their following states in one pass, suitable for `iterate`."
  [win-score world]
  (reduce (fn [world [state n]]
            (let [{p1 0 p2 1 remain nil} (->> (dirac-poss state n)
                                              (group-by #(winner win-score (key %))))]
              (-> world
                  (update-in [:winners 0] + (reduce + (map second p1)))
                  (update-in [:winners 1] + (reduce + (map second p2)))
                  (update :state-hash dissoc state)
                  (update :state-hash #(merge-with + % (into {} remain))))))
          world
          (:state-hash world)))

(defn make-dirac
  "Initial world state with given player positions."
  [pos]
  {:winners    [0 0]
   :state-hash {{:pos pos :scores [0 0] :player 0} 1}})

;; ^:blog We terminate when there are no more states that aren't winners.

(defn ^:blog part-2 [input]
  (->> (make-dirac input)
       (iterate (partial step-dirac 21))
       (find-first #(empty? (:state-hash %)))
       :winners
       (reduce max)))
