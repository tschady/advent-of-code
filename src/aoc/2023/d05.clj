(ns aoc.2023.d05
  (:require
   [aoc.file-util :as f]
   [aoc.string-util :as string-util]
   [clojure.string :as str]))

(def input (f/read-chunks "2023/d05.txt"))

(defn parse-seeds [s] (map #(vector "seed" %) (string-util/ints s)))

(defn parse-seeds3 [s]
  (->> (string-util/ints s)
       (partition 2)
       (map (fn [[start len]] [start (+ -1 len start)]))
       (vector "seed")))

(defn parse-chunk [s]
  (let [[_ src dest] (re-find #"^(.*?)-to-(.*?) map:" s)
        tuples      (->> (string-util/ints s)
                         (partition 3))]
    {src {:dest dest :usage tuples}}))

(defn parse-chunk3 [s]
  (let [[_ src dest] (re-find #"^(.*?)-to-(.*?) map:" s)
        tuples       (->> (string-util/ints s)
                          (partition 3)
                          (map (fn [[dest src len]]
                                 [src (+ -1 src len) dest])))]
    {src {:dest dest :usage tuples}}))

(defn explode-ranges [curr usage]
  (reduce (fn [curr r] (mapcat (partial chranges r) curr))
          curr
          usage))

(defn clear-meta [coll] (map #(vary-meta % dissoc :altered) coll))

(defn map-num [n ranges]
  (reduce (fn [n [dest src len]]
            (if (<= src n (+ src len))
              (reduced (+ dest (- n src)))
              n))
          n
          ranges))

(defn chranges
  [[x y d :as r] [a b :as orig] ]
  (cond
    (:altered (meta orig)) (list orig)            ;; already altered
    (or (< b x) (> a y)) (list orig)              ;; totally outside
    (< a x) (conj (chranges r [x b]) [a (dec x)]) ;; outside L
    (> b y) (conj (chranges r [a y]) [(inc y) b]) ;; outside R
    :else ;; inside, map it and set altered flag
    (list (with-meta [(+ d (- a x)) (+ d (- b x))] {:altered true}))))

(defn find-target
  [m target [src n]]
  (if (= src target)
    n
    (recur m target [(get-in m [src :dest]) (map-num n (get-in m [src :usage]))])))

(defn find-target3
  [m target [src xs]]
  (if (= src target)
    xs
    (recur m target [(get-in m [src :dest]) (clear-meta (explode-ranges xs (get-in m [src :usage])))])))

(defn part-1 [input]
  (let [seeds (parse-seeds (first input))
        m     (into {} (map parse-chunk (rest input)))]
    (reduce min (map (partial find-target m "location") seeds))))

(defn part-3 [input]
  (let [seeds (parse-seeds3 (first input))
        m     (into {} (map parse-chunk3 (rest input)))]
    (reduce min (map first (find-target3 m "location" seeds)))))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
(def ex-str "seeds: 79 14 55 13

seed-to-soil map:
50 98 2
52 50 48

soil-to-fertilizer map:
0 15 37
37 52 2
39 0 15

fertilizer-to-water map:
49 53 8
0 11 42
42 0 7
57 7 4

water-to-light map:
88 18 7
18 25 70

light-to-temperature map:
45 77 23
81 45 19
68 64 13

temperature-to-humidity map:
0 69 1
1 0 69

humidity-to-location map:
60 56 37
56 93 4")

(def ex (str/split ex-str #"\n\n"))


#_(part-1 input);178159714

#_(part-3 ex);46

#_(time (part-3 input));100165128

