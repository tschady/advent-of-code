(ns aoc.2015.d22
  (:require [aoc.file-util :as file-util]
            [com.rpl.specter :refer [setval transform ALL NONE]]))

(def boss-stats (->> (file-util/read-file "2015/d22.txt")
                     (re-seq #"\d+")
                     (map #(Integer/parseInt %))
                     (zipmap [:hp :damage])))

(def initial-state {:hard-mode      false
                    :hero           {:hp 50, :mana 500, :armor 0}
                    :boss           boss-stats
                    :current-player :hero
                    :active-spells  '()
                    :mana-spent     0})

(def spellbook [{:name "Magic Missile" :cost 53  :zap 4}
                {:name "Drain"         :cost 73  :zap 2 :heal 2}
                {:name "Shield"        :cost 113 :effect {:turns 6 :armor 7}}
                {:name "Poison"        :cost 173 :effect {:turns 6 :damage 3}}
                {:name "Recharge"      :cost 229 :effect {:turns 5 :mana 101}}])

(defn castable-spells
  "Returns list of all castable spells given total `mana` and current `active` spells.
  A spell is uncastable if you do not have sufficient mana or it is currently active."
  [mana active]
  (filter (fn [spell] (and (<= (:cost spell) mana)
                           (not-any? #(= (:name spell) (:name %)) active))) spellbook))

(defn- cast-spell [state spell]
  (-> state
      (update-in [:hero :mana] - (:cost spell))
      (update :mana-spent + (:cost spell))
      (update-in [:hero :hp] + (get spell :heal 0))
      (update-in [:boss :hp] - (get spell :zap 0))
      (update :active-spells #(if (contains? spell :effect) (conj % spell) %))))

(defn- update-timers
  "Returns `state` with all spell timers decremented, and expired spells removed."
  [state]
  (->> state
       (transform [:active-spells ALL :effect :turns] dec)
       (setval [:active-spells ALL #(zero? (get-in % [:effect :turns]))] NONE)))

(defn- apply-effects [state]
  (let [effects (apply merge-with + (map :effect (:active-spells state)))]
    (-> state
        (update-in [:boss :hp] - (get effects :damage 0))
        (update-in [:hero :mana] + (get effects :mana 0))
        (assoc-in [:hero :armor] (get effects :armor 0)))))

(defn apply-modes [state]
  (if (and (= :hero (:current-player state)) (:hard-mode state))
    (-> state
        (update-in [:hero :hp] dec)
        (assoc-in [:hero :dead] (>= 0 (get-in state [:hero :hp])))) ;; short-circuit
    state))

(defn- next-states
  "Return collection of all possible next states from given `state`.  For boss turns,
  this will just be a collection of one.  For player turns, there will be a possible
  future for each spell they may cast."
  [state]
  (let [player (:current-player state)
        state' (-> state
                   apply-modes
                   apply-effects
                   (assoc :current-player (if (= :hero player) :boss :hero))
                   update-timers)
        spells (castable-spells (get-in state' [:hero :mana]) (get state' :active-spells))
        boss-hit (max 1 (- (get-in state' [:boss :damage]) (get-in state' [:hero :armor])))]
    (if (= player :boss)
      (vector (update-in state' [:hero :hp] - boss-hit))
      (for [spell spells]
        (cast-spell state' spell)))))

(defn- play-game
  "Returns the least amount of mana the hero can spend to defeat the boss without dying."
  [{:keys [best states]}]
  (if (seq states)
    (recur
     ;; We go through every possibility using Breadth First Search.
     ;;  The outer loop goes through all current possible states.  The inner loop
     ;;  iterates over each of these, producing all possible next future states.
     ;;  We discard ones where we lose or can't win, saving new best mana wins, or
     ;;  passing along these non-terminated games to the next run through the outer loop.
     (reduce (fn [acc state]
               (reduce (fn [inner-acc inner-state]
                         (cond
                           ;; short-circuit hardmode death
                           (get-in inner-state [:hero :dead])
                           inner-acc

                           ;; over-spent, skip
                           (> (:mana-spent inner-state) (:best inner-acc))
                           inner-acc

                           ;; new hero winner
                           (>= 0 (get-in inner-state [:boss :hp]))
                           (assoc inner-acc :best (:mana-spent inner-state))

                           ;; boss wins, skip
                           (>= 0 (get-in inner-state [:hero :hp]))
                           inner-acc

                           ;; add new state
                           :else (update-in inner-acc [:states] conj inner-state)))
                       acc
                       (next-states state)))
             {:best   best
              :states []}
             states))
    best))

(defn part-1 [state] (play-game {:best Integer/MAX_VALUE
                                 :states [state]}))

(defn part-2 [state] (play-game {:best Integer/MAX_VALUE
                                 :states [(assoc state :hard-mode true)]}))
