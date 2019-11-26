(ns aoc.2015.d15
  (:require [aoc.file-util :as file-util]))

(def input (file-util/read-lines "2015/d15.txt"))

(defn parse-line [line] (mapv read-string (re-seq #"-?\d+" line)))

(def tsp-combos
  "A list of vectors of all combinations of 4-tuple with sum 100."
  (let [limit 101]
    (for [a (range 0 limit)
          b (range 0 (- limit a))
          c (range 0 (- limit a b))]
      (let [d (- limit 1 a b c)]
        [a b c d]))))

(defn bake-cookie
  "Return map of `:score` cookie-score and `:cal` calories for cookie,
  given list of ingredients with qualities `ingred-stats` and
  and ingredient counts given by `tsps`."
  [ingred-stats tsps]
  (let [parts (->> ingred-stats
                   (map (fn [tsp stat] (map #(* tsp %) stat)) tsps)
                   (apply map +)
                   (mapv #(max 0 %)))
        cal (peek parts)
        score (reduce * (pop parts))]
    {:score score :cal cal}))

(defn- bake-all-batches [input]
  (let [cookie-stats (map parse-line input)]
    (map #(bake-cookie cookie-stats %) tsp-combos)))

(defn part-1
  "Return the highest possible cookie-score given `input` ingredients."
  [input]
  (->> (bake-all-batches input)
       (map :score)
       (apply max)))

(defn part-2
  "Return the highest possible cookie-score given `input` ingredients,
  with calories being exactly 500."
  [input]
  (->> (bake-all-batches input)
       (filter #(= 500 (:cal %)))
       (map :score)
       (apply max)))
