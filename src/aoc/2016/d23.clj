(ns aoc.2016.d23
  (:require [aoc.2016.assembunny :as bunny]
            [aoc.file-util :as file-util]))

(def input (file-util/read-lines "2016/d23.txt"))

(defn part-1 [prog] (bunny/run prog (assoc bunny/init-state "a" 7)))

(defn part-2 [prog] (bunny/run prog (assoc bunny/init-state "a" 12)))
