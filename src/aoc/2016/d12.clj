(ns aoc.2016.d12
  (:require [aoc.file-util :as file-util]
            [com.rpl.specter :as specter :refer [transform setval]]
            [clojure.string :as str]))

(def input (file-util/read-lines "2016/d12.txt"))

(defn *cmd->fn [cmd]
  (let [[op a & b] (str/split cmd #" ")]
    (case op
      "cpy" (fn [mem] (assoc! mem (first b) (or (get mem a) (Integer/parseInt a))))
      "inc" (fn [mem] (assoc! mem a (inc (get mem a))))
      "dec" (fn [mem] (assoc! mem a (dec (get mem a))))
      "jnz" (fn [mem] (if (not= 0 (or (get mem a) (Integer/parseInt a)))
                             (assoc! mem :ptr (+ (get mem :ptr) -1 (Integer/parseInt (first b))))
                             mem))
      (throw (AssertionError. (str "Illegal input: " cmd))))))

(def cmd->fn (memoize *cmd->fn))

(defn run
  ([prog] (run prog {:ptr 0 "a" 0 "b" 0 "c" 0 "d" 0}))
  ([prog memory]
   (loop [mem (transient memory)]
     (if-let [cmd (get prog (get mem :ptr))]
       (recur (-> mem
                  ((cmd->fn cmd))
                  (assoc! :ptr (inc (get mem :ptr)))))
       (persistent! mem)))))

(defn part-1 [input] (-> input run (get "a")))

(defn part-2 [input] (-> input
                         (run {:ptr 0 "a" 0 "b" 0 "c" 1 "d" 0})
                         (get "a")))
