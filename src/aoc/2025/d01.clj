(ns aoc.2025.d01
  (:require
   [aoc.file-util :as f]
   [clojure.string :as str]))

(def input (map read-string (-> (f/read-file "2025/d01.txt")
                                (str/escape {\L \- \R \+})
                                (str/split-lines))))

(defn zero-touches [pos d]
  (let [pos' (+ pos d)
        wraps (abs (quot pos' 100))]
    ;; Hack for negative mod math, include leftwards that touch zero or cross it
    (if (or (zero? pos') (< pos' 0 pos))
      (inc wraps)
      wraps)))

(defn solve [input]
  (reduce (fn [[exact total pos] d]
            (let [pos' (mod (+ pos d) 100)]
              [(cond-> exact (zero? pos') inc)
               (+ total (zero-touches pos d))
               pos']))
          [0 0 50]
          input))

(defn part-1 [input] (first (solve input)))

(defn part-2 [input] (second (solve input)))
