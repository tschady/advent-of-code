(ns aoc.2021.d07
  (:require [aoc.file-util :as f]
            [aoc.math-util :as m]))

(def input (sort (f/read-ints "2021/d07.txt")))

(defn total-fuel
  "Returns the total fuel used by all the crabs at locations `locs` to reach point `dest`,
  using function `f` to compute fuel required for each distance."
  [locs f dest]
  (reduce + (map (comp f #(Math/abs (- % dest))) locs)))

;; Because the costs are linear with distance, we know the median will be the meeting point.
(defn part-1 [input]
  (total-fuel input identity (m/median input)))

;; The answer will be within 0.5 of the mean, so test the two integers around it.
;; I originally used the mean as an upper bound, since I knew the triangle number was < n^2
;; via https://www.reddit.com/r/adventofcode/comments/rawxad/2021_day_7_part_2_i_wrote_a_paper_on_todays/
(defn part-2 [input]
  (let [mean (m/mean input)
        low (int (Math/floor mean))
        hi (int (Math/ceil mean))]
    (->> (range low (inc hi))
         (map (partial total-fuel input m/series-sum))
         (apply min))))
