(ns aoc.2015.d07
  (:require [aoc.file-util :as file-util]
            [clojure.string :as str]))

(def input (into '() (file-util/read-lines "2015/d07.txt")))

(def memory (atom {}))

(defn alloc-registers! [memory prog]
  (let [registers (->> prog
                        (str/join "\n")
                        (re-seq #"-> (\w+)")
                        (map second))]
    (run! #(swap! memory assoc % (promise)) registers)))

(defn- val-or-register [memory s]
  (if (re-matches #"\d+" s)
    (Integer/parseInt s)
    (deref (get @memory s))))

(defn exec-inst!
  "Execute instruction `inst`, setting result in requested location of
  `memory`.  Instruction blocks until its input registers have been set."
  [memory inst]
  (let [[_ ops reg] (re-matches #"(.*) -> (\w+)" inst)]
    (future
      (deliver (get @memory reg)
               (condp re-matches ops
                 #"(\w+) AND (\w+)" :>> ;; bitwise AND
                 (fn [[_ r1 r2]]
                   (let [arg1 (val-or-register memory r1)
                         arg2 (val-or-register memory r2)]
                     (bit-and arg1 arg2)))

                 #"(\w+) OR (\w+)" :>> ;; bitwise OR
                 (fn [[_ r1 r2]]
                   (let [arg1 (val-or-register memory r1)
                         arg2 (val-or-register memory r2)]
                     (bit-or arg1 arg2)))

                 #"NOT (\w+)" :>> ;; bitwise NOT
                 (fn [[_ r1]] (bit-not (deref (get @memory r1))))

                 #"(\w+) LSHIFT (\d+)" :>> ;; bitshift left
                 (fn [[_ r1 r2]] (bit-shift-left (deref (get @memory r1))
                                                 (Integer/parseInt r2)))

                 #"(\w+) RSHIFT (\d+)" :>> ;; bitshift right
                 (fn [[_ r1 r2]] (bit-shift-right (deref (get @memory r1))
                                                  (Integer/parseInt r2)))

                 #"([a-z]+)" :>> ;; register copy
                 (fn [[_ r1]] (deref (get @memory r1)))

                 #"(\d+)" :>> ;; register set
                 (fn [[_ r1]] (Integer/parseInt r1)))))))

(defn- run-prog! [memory prog]
  (reset! memory {})
  (alloc-registers! memory prog)
  (doseq [inst prog]
    (exec-inst! memory inst)))

(defn part-1 [prog]
  (run-prog! memory prog)
  (deref (get @memory "a")))

(defn part-2 [prog]
  (let [b-override (part-1 prog)
        prog' (conj prog (str b-override " -> b"))]
    (part-1 prog')))
