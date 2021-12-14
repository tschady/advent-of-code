(ns aoc.2021.d14
  (:require
   [aoc.coll-util :refer [x-nth]]
   [aoc.file-util :as file-util]))

(def input (file-util/read-chunks "2021/d14.txt"))

(defn parse [[template rules]]
  [template
   (->> rules
        (re-seq #"(\w\w) -> (\w)")
        ;; holy destructuring batman.  Went crazy to get chars instead of strings
        (map (fn [[_ [a b] [insert]]] [[a b] insert]))
        (into {}))])

;; ^:blog
;; For part-1, I raced to an iterative solution building the string each time with
;; `medley.core/interleave-all`.
;; 10 iterations took 20ms, 20 took 1000x that, so there's no way we can do this 40 times.
;; Looking at the ruleset, it's pretty contained, so we should be able to just track
;; counts of each pair.  Very similar to day 6 for fish count.

(defn ^:blog step [rules pair-counts]
  (reduce-kv (fn [m [a b :as k] v]
               (let [insert (get rules k)]
                 (-> m
                     (update [a insert] (fnil + 0) v)
                     (update [insert b] (fnil + 0) v))))
             {}
             pair-counts))

(defn- score [letter-freq]
  (- (apply max (vals letter-freq))
     (apply min (vals letter-freq))))

(defn assemble
  "Returns the frequencies of each letter in the polymer, by taking the first letter
  from each `pairs` frequency, and adding in the last letter `end` of the template."
  [end pairs]
  (reduce-kv (fn [m [a _] v]
               (update m a (fnil + 0) v))
             {end 1}
             pairs))

;; ^:blog
;; The only thing of interest here is `x-nth`, a utility function I wrote that just
;; reverses the arguments of `nth` in order to make thread-last work.

(defn ^:blog solve [input n]
  (let [[orig rules] (parse input)]
    (->> orig
         (partition 2 1)
         frequencies
         (iterate (partial step rules))
         (x-nth n)
         (assemble (last orig))
         score)))

(defn part-1 [input] (solve input 10))

(defn part-2 [input] (solve input 40))
