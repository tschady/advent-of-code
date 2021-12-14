(ns aoc.2021.d01
  (:require
   [aoc.coll-util :refer [intervals]]
   [aoc.file-util :as file-util]))

(def input (file-util/read-ints "2021/d01.txt"))

;; ^:blog
;; The trick here is realizing that `(B + C + D) - (A + B + C) = D - A`.
;; I originally used the 3-arity version of map with
;; `(count (filter pos? (map - (rest input) input)))`
;; but then realized that was a utility fn I'd forgotten about: `intervals`, for
;; the speed competition I never enter.

(defn ^:blog part-1 [input]
  (count (filter pos? (intervals input))))

(defn ^:blog part-2 [input]
  (count (filter pos? (intervals 3 input))))
