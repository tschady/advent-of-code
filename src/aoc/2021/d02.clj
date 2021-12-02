(ns aoc.2021.d02
  (:require
   [aoc.file-util :as file-util]
   [clojure.string :as str]))

(def input (file-util/read-lines "2021/d02.txt"))

(defn parse-cmd [s]
  (let [[dir n] (str/split s #" ")]
    [dir (Long/parseLong n)]))

(defn travel [input]
  (reduce (fn [[x y aim] [dir n]]
            (case dir
              "forward" [(+ x n) (+ y (* aim n)) aim]
              "down"    [x y (+ aim n)]
              "up"      [x y (- aim n)]))
          [0 0 0]
          (map parse-cmd input)))

(defn part-1 [input]
  (let [[x _ y] (travel input)]
    (* x y)))

(defn part-2 [input]
  (let [[x y _] (travel input)]
    (* x y)))
