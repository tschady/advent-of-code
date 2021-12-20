(ns aoc.2021.d20
  (:require
   [aoc.coll-util :refer [x-nth]]
   [aoc.file-util :as file-util]
   [aoc.grid :as grid]
   [aoc.string-util :as string-util :refer [s->int]]
   [clojure.string :as str]
   [medley.core :as medley :refer [filter-vals]]))

(def input (file-util/read-chunks "2021/d20.txt"))

;; ^:blog
;; Overall approach - use a map of [x y] to contents as usual.
;; Normally, we could just store the 1s, and omit the zeros, but this
;; problem has special consideration around Infinity. We start with an
;; infinite board of darkness, and my input "algorithm" turns any fully
;; dark square on (index 0 is `#`), and fully light squares off
;; (index 512 is `0`). On odd iterations, the board will be infinitely bright.
;; On even, infinitely dark.  If the problem asked for odd iterations,
;; I could track the bounded dark squares, but since they only ask for even
;; input I'll skip that and cheat with the `field` value below.  This is
;; an infinite cycle representing what the outskirts look like at the time.

(def ^:blog glyph->val {\# \1 \. \0})

(defn ^:blog parse [[algo-str img-str]]
  (let [img-lines (str/split-lines img-str)]
    {:low   0
     :hi    (count img-lines)
     :field (cycle [\0 \1])
     :img   (grid/build-grid img-lines glyph->val)
     :algo  (mapv glyph->val algo-str)}))

;; ^:blog When checking for surrounding values, if it's out of bounds,
;; it's part of the infinite field.

(defn ^:blog new-val [algo img loc default]
  (let [locs (grid/neighbor-coords loc (grid/area-deltas 1))]
    (->> locs
         (map #(get img % default))
         (s->int 2)
         (get algo))))

;; ^:blog Use a typical step function for iteration.  Caching the current
;; range extremities in `low` and `hi` saves a bit of time.

(defn ^:blog step [{:keys [low hi img algo field] :as state}]
  (let [low  (dec low)
        hi   (inc hi)
        span (range low hi)
        locs (for [x span y span] [x y])]
    (reduce (fn [state loc]
              (assoc-in state [:img loc] (new-val algo img loc (first field))))
            (-> state
                (assoc :low low)
                (assoc :hi hi)
                (update :field rest))
            locs)))

(defn ^:blog solve [input n]
  (->> (parse input)
       (iterate step)
       (x-nth n)
       :img
       (filter-vals #{\1})
       count))

(defn part-1 [input] (solve input 2))

(defn part-2 [input] (solve input 50))

;; ^:blog This is pretty slow (5s for part-2 on my machine).
;; Replacing the `(iterate step state)` with a loop over a transient
;; should be much faster
