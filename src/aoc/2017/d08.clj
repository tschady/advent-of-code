(ns aoc.2017.d08
  (:require [aoc.file-util :as file-util]))

(def input (file-util/read-lines "2017/d08.txt"))

(def cmd-patt #"^(\w+) (inc|dec) (-?\d+) if (\w+) (.*?) (-?\d+)$")

(defn parse-command [s]
  (let [[_ r1 op1 a1 r2 op2 a2] (re-find cmd-patt s)]
    {:r1 r1
     :op1 (if (= "dec" op1) - +)
     :a1 (Long/parseLong a1)
     :r2 r2
     :op2 (if (= op2 "!=")
            not=
            (resolve (symbol op2)))
     :a2 (Long/parseLong a2)}))

(defn exec-cmd [regs {:keys [r1 op1 a1 r2 op2 a2]}]
  (if (op2 (get regs r2 0) a2)
    (update regs r1 (fnil op1 0) a1)
    regs))

(defn part-1 [input]
  (->> input
       (map parse-command)
       (reduce exec-cmd {})
       (vals)
       (apply max)))

(defn part-2 [input]
  (->> input
       (map parse-command)
       (reductions exec-cmd {})
       (mapcat vals)
       (apply max)))
