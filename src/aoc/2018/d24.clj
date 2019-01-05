(ns aoc.2018.d24
  (:require [aoc.file-util :as file-util]
            [clojure.string :as str]
            [instaparse.core :as insta]
            [clojure.edn :as edn]))

(def input (file-util/read-file "2018/d24.txt"))

(def grammar
  "battle = army army
  army = army-name <':'> groups
  army-name = #'[\\w ]+'
  groups = (group)+
  group = size <' units each with '>
          hp <' hit points'>
          defense
          attack
          <' at initiative '> init
  size = #'[0-9]+'
  hp = #'[0-9]+'
  dmg = #'[0-9]+'
  init = #'[0-9]+'
  <defense> = <' ('> (weak <'; '>? | immune <'; '>?)+ <')'> | Epsilon
  <attack> = <' with an attack that does '> dmg <' '> dmg-type <' damage'>
  weak = <'weak to '> (dmg-type <', '>?)+
  immune = <'immune to '> (dmg-type <', '>?)+
  dmg-type = 'bludgeoning' | 'cold' | 'fire' | 'radiation' | 'slashing'")

(def normalize
  "Turn Hiccup format into more usable hashmap.
  FIXME: there might be an easier way, this seems heavy."
  {:hp #(hash-map :hp (edn/read-string %))
   :init #(hash-map :init (edn/read-string %))
   :size #(hash-map :size (edn/read-string %))
   :dmg #(hash-map :dmg (edn/read-string %))
   :dmg-type #(hash-map :dmg-type %)
   :weak #(hash-map :weak (set (mapcat vals %&)))
   :immune #(hash-map :immune (set (mapcat vals %&)))
   :group #(apply merge %&)
   :groups list
   :army (fn [[key name] groups] (map #(assoc % key name) groups))
   :battle (fn [& args] (->> args
                             (apply concat)
                             (map-indexed #(assoc %2 :id %1))))})

(defn ->battle
  "Construct a battle from input by parsing into list of battle groups."
  [input]
  (->> input
       ((insta/parser grammar :auto-whitespace :standard))
       (insta/transform normalize)))

(defn- effective-power [group] (* (:dmg group) (:size group)))

(defn game-over?
  "Returns true if only one army left, else false."
  [battle]
  (= 1 (count (group-by :army-name battle))))

(defn score
  "Returns the sum of all group sizes."
  [battle]
  (reduce + 0 (map :size battle)))

(defn def-mod
  "Returns damage modifier based on attack damage type and any defenses.
  Immunity removes all damage, weakness doubles it."
  [dmg-type immunities weaknesses]
  (condp contains? dmg-type
    immunities 0
    weaknesses 2
    1))

(defn calc-dmg
  "Return the total damage of an attacker vs. a defender, considering damage & defense types."
  [attacker defender]
  (let [base-dmg (effective-power attacker)
        modifier (def-mod (:dmg-type attacker) (:immune defender) (:weak defender))]
    (* base-dmg modifier)))

(defn calc-losses
  "Return how many units of the defender are killed in an attack."
  [defender dmg]
  (quot dmg (:hp defender)))

(defn attack-order [groups]
  (sort-by :init > groups))

(defn determine-fights
  "Return a list of tuples of intended [attacker-id defender-id] for `battle` groups.
  e.g. '([0 3] [1 2] [3 1])  =>  0 attacks 3, 1 attacks 2, 3 attacks 1"
  [battle]
  (:pairs
   (reduce (fn [acc attacker]
             (let [enemies (->> (:available acc)
                                (remove #(= (:id attacker) (:id %)))
                                (remove #(= (:army-name attacker) (:army-name %))))]
               ;; TODO: better way to do all this empty/nil checking?
               ;;   can't do max-key on empty collection.
               (if (empty? enemies)
                 acc
                 (let [defender (apply max-key #(calc-dmg attacker %) enemies)]
                   (if (zero? (calc-dmg attacker defender))
                     acc
                     (-> acc
                         (update :pairs conj [(:id attacker) (:id defender)])
                         (update :available #(remove (fn [x] (= (:id defender) (:id x))) %))))))))
           {:pairs [] :available battle}
           (attack-order battle))))

(defn resolve-damage [battle [att-id def-id]]
  (if-let [attacker (first (filter #(= att-id (:id %)) battle))]
    (let [defender (first (filter #(= def-id (:id %)) battle))
          damage (calc-dmg attacker defender)
          losses (calc-losses defender damage)
          remaining-defenders (max 0 (- (:size defender) losses))]
      (println defender)
      (println damage)
      (println losses)
      (println remaining-defenders)
      (if (zero? remaining-defenders)
        (remove #(= def-id (:id %)) battle)
        (map #(if (= def-id (:id %))
                (assoc % :size remaining-defenders)
                %)
             battle)))
    battle))

(defn simulate
  ""
  [battle]
  (if (game-over? battle)
    #_(score battle)
    battle
    (recur (reduce resolve-damage battle (determine-fights battle)))))

;;;;
(def test-input
  "Immune System:
17 units each with 5390 hit points (weak to radiation, bludgeoning) with an attack that does 4507 fire damage at initiative 2
989 units each with 1274 hit points (immune to fire; weak to bludgeoning, slashing) with an attack that does 25 slashing damage at initiative 3

Infection:
801 units each with 4706 hit points (weak to radiation) with an attack that does 116 bludgeoning damage at initiative 1
4485 units each with 2961 hit points (immune to radiation; weak to fire, cold) with an attack that does 12 slashing damage at initiative 4")


(def battle (->battle test-input))
;battle
(determine-fights battle);[[2 1] [0 3] [3 0] [1 2]]

(calc-dmg (nth battle 3) (second battle));107640

(calc-losses (second battle) 107640)

(resolve-damage battle [2 1])

(max 0 (- 989 84))

;(score (simulate (->battle input)))

(:size (first (filter #(= 1 (:id %)) battle)))

(reduce resolve-damage battle (determine-fights battle))

(determine-fights battle);[[3 1] [1 3] [0 2] [2 0]]

(simulate (->battle test-input))


(calc-dmg (last battle) (first battle))

(apply hash-map [:a 1 :b 2]);


(attack-order battle)


(sort-by (juxt effective-power :init) battle)
