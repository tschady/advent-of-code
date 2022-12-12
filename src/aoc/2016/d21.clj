(ns aoc.2016.d21
  (:require
   [aoc.file-util :as file-util]
   [aoc.string-util :as s]
   [clojure.string :as str]))

(def input (file-util/read-lines "2016/d21.txt"))

(defn rotate-based [s c]
  (let [i (.indexOf s c)
        n (if (>= i 4)
            (+ 2 i)
            (+ 1 i))]
    (s/rotate-right s n)))

(defn move-pos [s i j]
  (let [letter (nth s i)
        removed (concat (take i s) (drop (inc i) s))]
    (concat (take j removed)
            (list letter)
            (drop j removed))))

(defn reverse-pos [s i j]
  (concat (take i s)
          (reverse (take (inc (Math/abs (- j i))) (drop i s)))
          (drop (inc j) s)))

(defn get-single-chars [s] (mapcat seq (re-seq #"\b[a-z]\b" s)))

(defn transform [s instr]
  (condp (fn [test subj] (str/starts-with? subj test)) instr
    "swap position" (apply s/swap-pos s (s/ints instr))
    "swap letter"   (apply s/swap-letter s (get-single-chars instr))
    "rotate left"   (apply s/rotate-left s (s/ints instr))
    "rotate right"  (apply s/rotate-right s (s/ints instr))
    "rotate based"  (apply rotate-based s (get-single-chars instr))
    "reverse"       (apply reverse-pos s (s/ints instr))
    "move"          (apply move-pos s (s/ints instr))))

;; brute force this one until rolling forward is a match
(defn inv-rotate-based
  [s c]
  (->> (range (count s))
       (map #(rotate-left s %))
       (filter #(= s (rotate-based % c)))
       first))

(defn inv-transform [s instr]
  (condp (fn [test subj] (str/starts-with? subj test)) instr
    "swap position" (apply s/swap-pos s (reverse (s/ints instr)))
    "swap letter"   (apply s/swap-letter s (get-single-chars instr))
    "rotate left"   (apply s/rotate-right s (s/ints instr))
    "rotate right"  (apply s/rotate-left s (s/ints instr))
    "rotate based"  (apply inv-rotate-based s (get-single-chars instr))
    "reverse"       (apply reverse-pos s (s/ints instr))
    "move"          (apply move-pos s (reverse (s/ints instr)))))

(defn part-1 [s prog] (apply str (reduce transform (seq s) prog)))

(defn part-2 [s prog] (apply str (reduce inv-transform (seq s) (reverse prog))))
