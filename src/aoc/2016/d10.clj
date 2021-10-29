(ns aoc.2016.d10
  (:require [aoc.file-util :as file-util]
            [clojure.string :as str]
            [medley.core :as medley :refer [filter-vals]]))

(def input (file-util/read-lines "2016/d10.txt"))

(defn- extract-registers
  "Returns a list of strings representing all the registers included in the input line.
  e.g. 'bot 12 gives low to output 4 and high to bot 125' => ('bot 12' 'output 4' 'bot 125')"
  [line]
  (map first (re-seq #"((?:output|bot) \d+)" line)))

(defn- get-value [line] (Integer/parseInt (peek (re-find #"value (\d+)" line))))

(defn make-init-state
  "Builds a hashmap of :commands and :registers from text description.
  :commands is a map of a register to destinations for its high and low elements.
  :registers is a map of location to a list of its current values."
  [input]
  (reduce (fn [state line]
            (let [regs (extract-registers line)]
              (cond
                (str/starts-with? line "bot")
                (update-in state [:commands] into (hash-map (first regs) regs))

                (str/starts-with? line "value")
                (update-in state [:registers (first regs)] conj (get-value line)))))
          {:commands {} :registers {}}
          input))

(defn xfer-vals
  "Move the low value from `from` register to `low-to`, and high value to `hi-to`"
  [state [from low-to hi-to]]
  (let [values (get-in state [:registers from])]
    (-> state
        (update-in [:registers low-to] conj (apply min values))
        (update-in [:registers hi-to] conj (apply max values))
        (update-in [:registers] dissoc from))))

(defn step-state [state]
  (let [full-regs  (keys (filter-vals #(= 2 (count %)) (:registers state)))
        ready-cmds (map #(get-in state [:commands %]) full-regs)]
    (reduce xfer-vals state ready-cmds)))

(defn part-1 [input]
  (let [targets #{61 17}]
    (->> (make-init-state input)
         (iterate step-state)
         (map :registers)
         (map #(filter-vals (fn [x] (= targets (set x))) %))
         (remove empty?)
         first
         keys
         first)))

(defn part-2 [input]
  (let [targets ["output 0" "output 1" "output 2"]]
    (->> (make-init-state input)
         (iterate step-state)
         (map :registers)
         (drop-while #(not-every? % targets))
         first
         (#(select-keys % targets))
         vals
         (map first)
         (reduce *))))
