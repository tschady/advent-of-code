(ns aoc.2022.d02
  (:require [aoc.file-util :as file-util]
            [clojure.string :as str]))

;; I started with a lookup to get the answer quickly,
;; then golfed with mod math.  Shorter would be to use ASCII int vals
(def shape->val {"A" 1 "X" 1,
                 "B" 2 "Y" 2,
                 "C" 3 "Z" 3})

(def input (file-util/read-lines "2022/d02.txt"))

(defn normalize [input] (map (comp #(map shape->val %) #(str/split % #" ")) input))

(defn score1 [[elf player]] (+ player (* 3 (mod (inc (- player elf)) 3))))

(defn score2 [[elf player]] (+ (* 3 (dec player)) (inc (mod (+ player elf) 3))))

(defn solve [score-fn input] (reduce + (map score-fn (normalize input))))

(defn part-1 [input] (solve score1 input))

(defn part-2 [input] (solve score2 input))
