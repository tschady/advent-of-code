(ns aoc.2019.d02
  (:require [aoc.file-util :as file-util]
            [clojure.string :as str]))

(def input (mapv read-string (str/split (file-util/read-file "2019/d02.txt") #",")))

(def opcodes {1 +, 2 *, 99 :terminate})

(defn- run-step [tape [opcode p1 p2 result-pos]]
  (let [v1 (tape p1)
        v2 (tape p2)
        result ((get opcodes opcode) v1 v2)]
    (assoc tape result-pos result)))

(defn run-tape
  ([tape noun verb] (run-tape (assoc tape 1 noun 2 verb)))
  ([tape]
   (loop [tape tape
          pos  0]
     (if (= :terminate (get opcodes (tape pos)))
       tape
       (recur (run-step tape (subvec tape pos (+ 4 pos)))
              (+ 4 pos))))))

(defn part-1 [input] (first (run-tape input 12 2)))

(defn part-2 [input]
  (let [magic-output 19690720]
    (first (for [noun  (range 100)
                 verb  (range 100)
                 :let  [tape (run-tape input noun verb)]
                 :when (= magic-output (first tape))]
             (+ verb (* 100 noun))))))
