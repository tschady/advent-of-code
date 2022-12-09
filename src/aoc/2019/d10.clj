(ns aoc.2019.d10
  (:require
   [aoc.file-util :as file-util]
   [aoc.grid :as grid]
   [clojure.core.matrix :as mat]
   [com.rpl.specter :refer [MAP-VALS transform]]
   [medley.core :refer [remove-vals]]))

(def input (file-util/read-lines "2019/d10.txt"))

(defn parse-field [input]
  (set (keys (remove-vals nil? (grid/build-grid input {\# 1, \. nil})))))

(defn- rotate
  "Asteroid ray-tracing starts by pointing straight \"up\", then sweeping
  clockwise.  Since \"up\" is neg-y-axis, and arctan2 zeros around the
  x-axis, we rotate and flip point coords to use arctan2 correctly."
  [[x y]]
  [(* -1 y) x])

(defn- angle-groups
  "Return map of asteroid field with keys being angle (0-360) between
  `point` and each other member of `field`, and values being a vector of
  asteroid positions, sorted by increasing distance from `point`.
  i.e. the entry for asteroids to the 'right' of [0 0] would be
  {90 [[1 0] [5 0] [80 0]]}"
  [field point]
  (->> (disj field point)
       (group-by #(grid/angle (rotate (mat/sub % point))))
       (into (sorted-map))
       (transform [MAP-VALS] #(sort-by (partial grid/distance point) %))))

(defn optimal-station
  "For a given asteroid `field`, find the coords of the asteroid
  that has the most other asteroids in line-of-sight, and the count it sees.
  Returns tuple `[[x y] count]`"
  [field]
  (->> field
       (map #(vector % (count (angle-groups field %))))
       (apply max-key second)))

(defn destroy-seq
  "Returns the seq of asteroids of `field` to be destroyed by `station`"
  [station field]
  (->> (angle-groups field station)
       (map val)
       (grid/transpose-pad nil)
       (keep identity)))

(defn part-1 [input] (-> input parse-field optimal-station second))

(defn part-2 [input target-count]
  (let [field (parse-field input)
        station (first (optimal-station field))
        target-xy (nth (destroy-seq station field) (dec target-count))]
    (+ (* 100 (first target-xy)) (second target-xy))))
