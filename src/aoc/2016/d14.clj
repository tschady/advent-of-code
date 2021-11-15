(ns aoc.2016.d14
  (:require [aoc.file-util :as file-util]
            [digest]))

(def input (first (file-util/read-lines "2016/d14.txt")))

(defn *hashcode [salt n] (digest/md5 (str salt n)))
(def hashcode (memoize *hashcode))

(defn *stretched-hashcode [salt n] (nth (iterate digest/md5 (str salt n)) 2017))
(def stretched-hashcode (memoize *stretched-hashcode))

(defn first-triple [code] (second (re-find #"(.)\1{2,}" code)))

(defn *quintuples [code] (not-empty (set (map second (re-seq #"(.)\1{4,}" code)))))
(def quintuples (memoize *quintuples))

;; TODO: for further optimization, combine match3 and match5 checks, and only check for 5 if 3 happens

(defn valid-key? [hasher i]
  (when-let [match3 (first-triple (hasher i))]
    (reduce (fn [_ i]
              (let [match5s (quintuples (hasher i))]
                (when (contains? match5s match3)
                  (reduced true))))
            nil
            (range (inc i) (+ i 1001)))))

(defn find-key [hasher n]
  (->> (range)
       (filter (partial valid-key? hasher))
       (take n)
       last))

(defn part-1 [input] (find-key (partial hashcode input) 64))

(defn part-2 [input] (find-key (partial stretched-hashcode input) 64))
