(ns aoc.2019.d08
  (:require [aoc.file-util :as file-util]
            [clojure.string :as str]))

(def input (file-util/read-file "2019/d08.txt"))

(def image-h 6)
(def image-w 25)


;; part 1
(def layer (->> input
                (partition (* 6 25))
                (map frequencies)
                (apply min-key #(get % \0 0))))


(* (get layer \1) (get layer \2))  ;; 1920


;;; part 2
(def pixels
  (->> input
       (partition (* 6 25))
       (apply map list)
       (map #(remove #{\2} %))
       (map first)
       (partition 25)
       (map #(apply str %))))

(println (str/join "\n" pixels))
