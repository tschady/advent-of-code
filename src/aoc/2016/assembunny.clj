(ns aoc.2016.assembunny
  (:require [clojure.string :as str]))

(def init-state {:ptr 0 "a" 0 "b" 0 "c" 0 "d" 0})

(defn- val-or-int [mem a] (or (get mem a) (Integer/parseInt a)))

(defn- *cmd->fn [cmd]
  (let [[op a & b] (str/split cmd #" ")]
    (case op
      "cpy" (fn [mem] (assoc! mem (first b) (val-or-int mem a)))
      "inc" (fn [mem] (assoc! mem a (inc (get mem a))))
      "dec" (fn [mem] (assoc! mem a (dec (get mem a))))
      "jnz" (fn [mem] (if (not= 0 (val-or-int mem a))
                             (assoc! mem :ptr (+ (get mem :ptr) -1 (Integer/parseInt (first b))))
                             mem))
      (throw (AssertionError. (str "Illegal input: " cmd))))))

(def ^:private cmd->fn (memoize *cmd->fn))

(defn run [prog memory]
  (loop [mem (assoc! (transient memory) :prog prog)]
    (if-let [cmd (get-in mem [:prog (get mem :ptr)])]
      (recur (-> mem
                 ((cmd->fn cmd))
                 (assoc! :ptr (inc (get mem :ptr)))))
      (get (persistent! mem) "a"))))
