(ns aoc.2018.d10
  (:require
   [aoc.file-util :as f]
   [aoc.string-util :as s]
   [aoc.grid :as grid]
   [aoc.elfscript :as elfscript]))

(def input (f/read-lines "2018/d10.txt"))

(defn make-pixel [s]
  (let [[x y dx dy] (s/ints s)]
    {:x x :y y :dx dx :dy dy}))

(defn tick-pixel
  ([pixel] (tick-pixel 1 pixel))
  ([n {:keys [x y dx dy] :as pixel}]
   (-> pixel
       (update-in [:x] + (* n dx))
       (update-in [:y] + (* n dy)))))

(defn height
  "Returns the y-axis length spanned by pixels"
  [pixels]
  (abs (- (reduce max (map :y pixels))
          (reduce min (map :y pixels)))))

(defn steps-to-coalesce
  "Return the number of time ticks required to get bounding box to
  a reasonable font-height."
  [pixels]
  (let [h     (height pixels)
        ddydt (- h (height (map tick-pixel pixels)))]
    (int (/ h ddydt))))

;; Find out the y-acceleration of process, use that to determine time
;; required to get readout of correct size
(defn part-1 [input]
  (let [pixels (map make-pixel input)
        t (steps-to-coalesce pixels)]
    (->> pixels
         (map (partial tick-pixel t))
         (map (fn [{:keys [x y]}] [[x y] \#]))
         (into {})
         (grid/print-grid-to-array \.)
         (elfscript/ocr))))

(defn part-2 [input] (steps-to-coalesce (map make-pixel input)))
