(ns aoc.2021.d07
  (:require [aoc.file-util :as f]
            [aoc.math-util :as m]))

(def input (sort (f/read-ints "2021/d07.txt")))

;; ^:blog
;; Like many of these AOC problems, a HOF is a clean way to separate part-1 and
;; part-2 formulas.

(defn ^:blog total-fuel
  "Returns the total fuel used by all the crabs at locations `locs` to reach point `dest`,
  using function `f` to compute fuel required for each distance."
  [locs f dest]
  (reduce + (map (comp f #(Math/abs (- ^int % ^int dest))) locs)))

;; ^:blog
;; For part 1, because the costs are linear with distance, we know the median will be the meeting point.

(defn ^:blog part-1 [input]
  (total-fuel input identity (m/median input)))

;; ^:blog
;; For part 2, if the fuel formula was `n^2` then the mean would yield the exact answer.
;; But since formula is a triangle number, which is always less than the square, I first used the mean as an upper bound to reduce the search space.
;; Then I found
;; https://www.reddit.com/r/adventofcode/comments/rawxad/2021_day_7_part_2_i_wrote_a_paper_on_todays/[this beauty]
;; which shows the answer will be within 0.5 of the mean, so I test the 2 integers around it.

(defn ^:blog part-2 [input]
  (let [mean (m/mean input)
        low (int (Math/floor mean))
        hi (int (Math/ceil mean))]
    (->> (range low (inc hi))
         (map (partial total-fuel input m/series-sum))
         (apply min))))
