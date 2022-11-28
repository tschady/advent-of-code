(ns aoc.2016.d25
  (:require
   [aoc.2016.assembunny :as bunny]
   [aoc.file-util :as file-util]))

(def input (file-util/read-lines "2016/d25.txt"))

;; hack
(defn desired-output [a]
  (let [depth 10]
    (= (take depth (cycle [0 1]))
       (bunny/run-inf-output input (assoc bunny/init-state "a" a) depth))))

(defn part-1 [prog]
  (->> (drop 1 (range))
       (filter desired-output)
       first))
