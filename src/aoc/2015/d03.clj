(ns aoc.2015.d03
  (:require [aoc.file-util :as file-util]))

(def input (file-util/read-file "2015/d03.txt"))

(def symbol->step {\^ [0 1]
                   \v [0 -1]
                   \> [1 0]
                   \< [-1 0]})

(defn- locations
  "Return all locations visited on a Cartesian plane by walking 1 step at a
  time from origin.  Step directions determined by arrows defined in
  symbol->step map."
  [symbols]
  (reductions (partial mapv +) [0 0] (map symbol->step symbols)))

(defn part-1
  "Return the number of unique locations visited by Santa, who moves on
  a Cartesian plane based on step instructions given in input symbols."
  [symbols]
  (-> symbols locations set count))

(defn part-2
  "Return number of unique locations visited by santa and Robot Santa.
  They each alternate move instructions from the input string."
  [symbols]
  (let [[santa robot] (apply mapv vector (partition-all 2 symbols))]
    (count (set (concat (locations santa) (locations robot))))))
