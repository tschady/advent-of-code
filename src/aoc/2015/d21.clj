(ns aoc.2015.d21
  (:require [aoc.file-util :as file-util]
            [clojure.math.combinatorics :as combo]))

(def input (file-util/read-ints "2015/d21.txt"))

(def hero-hp 100)

(defn parse-boss [input] (zipmap [:hp :damage :armor] input))

(def shop {:weapons [{:cost 8   :damage 4 :armor 0}
                     {:cost 10  :damage 5 :armor 0}
                     {:cost 25  :damage 6 :armor 0}
                     {:cost 40  :damage 7 :armor 0}
                     {:cost 74  :damage 8 :armor 0}]
           :armor   [{:cost 0   :damage 0 :armor 0} ; = no purchase
                     {:cost 13  :damage 0 :armor 1}
                     {:cost 31  :damage 0 :armor 2}
                     {:cost 53  :damage 0 :armor 3}
                     {:cost 75  :damage 0 :armor 4}
                     {:cost 102 :damage 0 :armor 5}]
           :rings   [{:cost 0   :damage 0 :armor 0} ; = no purchase
                     {:cost 0   :damage 0 :armor 0} ; = no purchase
                     {:cost 25  :damage 1 :armor 0}
                     {:cost 50  :damage 2 :armor 0}
                     {:cost 100 :damage 3 :armor 0}
                     {:cost 20  :damage 0 :armor 1}
                     {:cost 40  :damage 0 :armor 2}
                     {:cost 80  :damage 0 :armor 3}]})

(defn hero-variants
  "Returns net stats of all possible combinations of hero's equipment purchases
  from given `shop` for constraints: 1 weapon, 0-1 armor, 0-2 rings"
  [hp shop]
  (for [weapon (:weapons shop)
        armor (:armor shop)
        [ring1 ring2] (combo/combinations (:rings shop) 2)]
    (assoc (merge-with + weapon armor ring1 ring2) :hp hp)))

(defn- turns-to-kill [hp damage armor]
  (let [hit-per-turn (max 1 (- damage armor))]
    (Math/ceil (/ hp hit-per-turn))))

(defn- hero-wins? [boss hero]
  (<= (turns-to-kill (:hp boss) (:damage hero) (:armor boss))
      (turns-to-kill (:hp hero) (:damage boss) (:armor hero))))

(defn part-1 [input hero-hp]
  (let [boss (parse-boss input)
        heros (hero-variants hero-hp shop)]
    (->> heros
         (filter #(hero-wins? boss %))
         (map :cost)
         (apply min))))

(defn part-2 [input hero-hp]
  (let [boss (parse-boss input)
        heros (hero-variants hero-hp shop)]
    (->> heros
         (remove #(hero-wins? boss %))
         (map :cost)
         (apply max))))
