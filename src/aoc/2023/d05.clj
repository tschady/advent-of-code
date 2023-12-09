(ns aoc.2023.d05
  (:require
   [aoc.file-util :as f]
   [aoc.string-util :as string-util]
   [clojure.string :as str]))

(def input (f/read-chunks "2023/d05.txt"))

(defn parse-seeds [v]
  (->> (partition 2 v)
       (map (fn [[start len]] [start (+ -1 len start)]))
       (vector "seed")))

(defn parse-usage [s]
  (let [[_ src dest] (re-find #"^(.*?)-to-(.*?) map:" s)
        tuples       (->> (string-util/ints s)
                          (partition 3)
                          (map (fn [[dest src len]]
                                 [src (+ -1 src len) dest])))]
    {src {:dest dest :usage tuples}}))

(defn chranges
  "Return new set of ranges of `orig` with any overlapping ranges in `[x y]`
  altered by the offset `d`."
  [[x y d :as r] [a b :as orig] ]
  (cond
    (:altered (meta orig)) (list orig)            ;; already altered
    (or (< b x) (> a y))   (list orig)              ;; totally outside
    (< a x)                (conj (chranges r [x b]) [a (dec x)]) ;; outside L
    (> b y)                (conj (chranges r [a y]) [(inc y) b]) ;; outside R
    :else ;; inside, map it and set altered flag
    (list (with-meta [(+ d (- a x)) (+ d (- b x))] {:altered true}))))

(defn clear-meta [coll] (map #(vary-meta % dissoc :altered) coll))

(defn update-ranges [curr usage]
  (clear-meta (reduce (fn [curr r] (mapcat (partial chranges r) curr))
                      curr
                      usage)))

(defn find-target
  [m target [src xs]]
  (if (= src target)
    xs
    (recur m target [(get-in m [src :dest]) (update-ranges xs (get-in m [src :usage]))])))

(defn solve [seed-fn [seed & usage]]
  (let [seeds (parse-seeds (seed-fn (string-util/ints seed)))
        m (into {} (map parse-usage usage))]
    (reduce min (map first (find-target m "location" seeds)))))

;; turn individual seeds into ranges with length 1
(defn part-1 [input] (solve #(interleave % (repeat 1)) input))

(defn part-2 [input] (solve identity input))
