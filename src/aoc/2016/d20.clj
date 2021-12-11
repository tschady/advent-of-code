(ns aoc.2016.d20
  (:require
   [aoc.file-util :as file-util]))

(def input (file-util/read-ranges "2016/d20.txt"))

(def max-ip 4294967295)

(defn- overlap? [[_ hi1] [lo2 _]] (>= 1 (- lo2 hi1)))

(defn- merge-ips [[lo1 hi1] [lo2 hi2]] [(min lo1 lo2) (max hi1 hi2)])

(defn merge-ranges [ranges r2]
  (let [r1 (peek ranges)]
    (if (overlap? r1 r2)
      (conj (pop ranges) (merge-ips r1 r2))
      (conj ranges r2))))

(defn allowed-ips [input]
  (->> input
       (sort-by (juxt first second))
       (reduce merge-ranges [[0 0]])
       (partition 2 1)
       (map (fn [[[_ hi1] [lo2 _]]] (range (inc hi1) lo2)))
       (apply concat)))

(defn part-1 [input] (first (allowed-ips input)))

(defn part-2 [input] (count (allowed-ips input)))
