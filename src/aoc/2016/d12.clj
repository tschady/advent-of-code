(ns aoc.2016.d12
  (:require [aoc.2016.assembunny :as bunny]
            [aoc.file-util :as file-util]
            [clojure.string :as str]))

(def input (file-util/read-lines "2016/d12.txt"))

(defn part-1 [input] (bunny/run input bunny/init-state))

(defn part-2 [input] (bunny/run input (assoc bunny/init-state "c" 1)))
