(ns aoc.2016.assembunny
  (:require [clojure.string :as str]))

(def init-state {:ptr 0 :out [] "a" 0 "b" 0 "c" 0 "d" 0})

(defn- val-or-int [mem a] (or (get mem a) (Integer/parseInt a)))

(defn- toggle-instr [s]
  (when s
    (case (first (str/split s #" "))
      "inc" (str/replace-first s "inc" "dec")
      "dec" (str/replace-first s "dec" "inc")
      "tgl" (str/replace-first s "tgl" "inc")
      "cpy" (str/replace-first s "cpy" "jnz")
      "jnz" (str/replace-first s "jnz" "cpy")
      s)))

(defn- next-ptr [mem a] (+ (get mem :ptr) (val-or-int mem a)))

(defn- *cmd->fn [cmd]
  (let [[op a & b] (str/split cmd #" ")]
    (try
      (case op
        "cpy" (fn [mem] (assoc! mem (first b) (val-or-int mem a)))
        "inc" (fn [mem] (assoc! mem a (inc (get mem a))))
        "dec" (fn [mem] (assoc! mem a (dec (get mem a))))
        "jnz" (fn [mem] (if (not= 0 (val-or-int mem a))
                          (assoc! mem :ptr (dec (next-ptr mem (first b))))
                          mem))
        "tgl" (fn [mem]
                (let [target (next-ptr mem a)]
                  (if-let [instr (get-in mem [:prog target])]
                    (assoc! mem :prog (assoc (get mem :prog) target (toggle-instr instr)))
                    mem)))
        "out" (fn [mem]
                (assoc! mem :out (conj (get mem :out) (val-or-int mem a)))))
      (catch Exception e (identity)))))

(def ^:private cmd->fn (memoize *cmd->fn))

(defn run [prog memory]
  (loop [mem (transient (assoc memory :prog prog))]
    (if-let [cmd (get-in mem [:prog (get mem :ptr)])]
      (recur (-> mem
                 ((cmd->fn cmd))
                 (assoc! :ptr (inc (get mem :ptr)))))
      (get (persistent! mem) "a"))))

(defn run-inf-output [prog memory limit]
  (loop [counter 0
         mem (transient (assoc memory :prog prog))]
    (if (>= (count (get mem :out)) limit)
      (get (persistent! mem) :out)
      (if-let [cmd (get-in mem [:prog (get mem :ptr)])]
          (recur (inc counter)
                 (-> mem
                     ((cmd->fn cmd))
                     (assoc! :ptr (inc (get mem :ptr)))))
          (get (persistent! mem) "a")))))
