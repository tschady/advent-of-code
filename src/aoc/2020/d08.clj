(ns aoc.2020.d08
  (:require [aoc.file-util :as file-util]
            [clojure.string :as str]))

(def input (file-util/read-lines "2020/d08.txt"))

(defn parse-instr [s]
  (let [[op arg] (str/split s #" ")]
    [(keyword op) (read-string arg)]))

(defn make-prog [input] (mapv parse-instr input))

(defn run [prog]
  (loop [seen #{}, ptr 0, acc 0]
    (cond
      (contains? seen ptr)  {:loop-detected acc}
      (>= ptr (count prog)) {:terminated acc}
      :else
      (let [[op arg]  (get prog ptr)
            next-seen (conj seen ptr)]
        (condp = op
          :nop (recur next-seen (inc ptr)   acc)
          :acc (recur next-seen (inc ptr)   (+ acc arg))
          :jmp (recur next-seen (+ ptr arg) acc))))))

(defn mutants
  "Returns all point mutations of `prog` via substitution map `change-fn`"
  [prog change-fn]
  (->> (range 0 (count prog))
       (keep #(when (change-fn (get-in prog [% 0]))
                (update-in prog [% 0] change-fn)))))

(defn part-1 [input]
  (:loop-detected (run (make-prog input))))

(defn part-2 [input]
  (->> (mutants (make-prog input) {:nop :jmp, :jmp :nop})
       (map run)
       (some :terminated)))
