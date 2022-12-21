(ns aoc.2022.d21
  (:require
   [aoc.file-util :as f]
   [clojure.string :as str]))

(def input (f/read-file "2022/d21.txt"))

(def memory (atom {}))

(defn alloc-registers! [memory prog]
  (let [registers (map second (re-seq #"(\w+):" (str/join "\n" prog)))]
    (run! #(swap! memory assoc % (promise)) registers)))

(defn exec-inst!
  "Execute instruction `inst`, setting result in requested location of
  `memory`.  Instruction blocks until its input registers have been set."
  [memory inst]
  (let [[_ reg ops] (re-matches #"(\w+): (.*)" inst)]
    (future (deliver (get @memory reg)
                     (condp re-matches ops
                       #"(\w+) ([\+\-\*/=]) (\w+)" :>> ;; math on registers
                       (fn [[_ r1 op r2]]
                         ((resolve (symbol op))
                          (deref (get @memory r1))
                          (deref (get @memory r2))))

                       #"(\d+)" :>> ;; register set
                       (fn [[_ r1]] (Integer/parseInt r1)))))))

(defn run-prog! [memory prog]
  (reset! memory {})
  (alloc-registers! memory prog)
  (doseq [inst prog]
    (exec-inst! memory inst)))

(defn part-1 [input]
  (let [prog (str/split-lines input)]
    (run-prog! memory prog)
    (deref (get @memory "root"))))

;; looking at the two halves of 'root', one was constant, the other
;; was linear with the human yell, so the answer was a simple linear
;; extrapolation
(defn part-2 [input]
  (let [p2 (str/replace-first input #"root: (\w+) . (\w+)" "root: $1 - $2")
        yell0 (part-1 (str/replace-first p2 #"humn: \d+" "humn: 0"))
        yell1 (part-1 (str/replace-first p2 #"humn: \d+" "humn: 1"))]
    (/ yell0 (- yell0 yell1))))
