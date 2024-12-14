(ns aoc.2024.d14
  (:require
   [aoc.file-util :as f]
   [aoc.grid :as grid]
   [medley.core :as medley :refer [take-upto]]))

(def input (partition 2 (partition 2 (f/read-ints "2024/d14.txt"))))

(defn wrap [[size-x size-y] [[x y] v]]
  [[(mod x size-x) (mod y size-y)] v])

(defn move [size t [p v]]
  (wrap size [(mapv + p (mapv #(* t %) v)) v]))

(defn quadrant [[size-x size-y] [[x y] _]]
  (let [x-line (quot size-x 2)
        y-line (quot size-y 2)]
    (cond
      (and (< x x-line) (< y y-line)) 0
      (and (> x x-line) (< y y-line)) 1
      (and (< x x-line) (> y y-line)) 2
      (and (> x x-line) (> y y-line)) 3)))

(defn part-1 [size input]
  (->> input
       (map (partial move size 100))
       (keep (partial quadrant size))
       frequencies
       vals
       (reduce *)))

(defn gridify [world] (zipmap (map first world) (repeat \*)))

(defn render [world] (grid/print (gridify world)))

(defn no-overlap?
  "Returns true if robots occupy unique positions."
  [world]
  (= (count world) (count (gridify world))))

(defn long-lines?
  "Returns true if there are at least 10 lines with 10 contiguous chars.
  Since we have 500 robots, here's hoping it'll have a block this big."
  [world]
  (< 10 (count (keep #(re-find #"\*\*\*\*\*\*\*\*\*\*" %) (render world)))))

;; first I tried symmetry (nope, it's not centered)
;; then I tried smallest bounding box (nope, there's snow in the pic)
;; then searched for long contiguous lines, checked render, got the answer
;; looked up other sol'ns, saw that there's no overlap in answer
(defn part-2 [size input]
  (->> input
       (iterate #(map (partial move size 1) %))
       (take-upto no-overlap?)
       count
       dec))
