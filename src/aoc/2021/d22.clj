(ns aoc.2021.d22
  (:require
   [aoc.coll-util :refer [tails]]
   [aoc.file-util :as file-util]
   [aoc.string-util :as string-util]))

(def input (file-util/read-lines "2021/d22.txt"))

(defn parse-line [s]
  (let [[_ cmd ranges] (re-find #"^(on|off)(.*)$" s)]
    [cmd (partition 2 (string-util/ints ranges))]))

;; ^:blog I looked at the data and saw the two halves, I cut/paste in
;; the obvious first section to not need a clamping function and get
;; part1 quickly using a naive set of currently lit coordinates.  The
;; input was small enough.
;;
;; For part2 this wouldn't work - even with bitpacking, it is TBs to
;; store the naive solution, so we can't track individual locations.  I
;; worked out the 2D case on paper to learn the algorithm, which is to
;; recursively remove all overlapping subcubes from each layer of the
;; instructions.  For 'on' cubes, we add their unique volume.  For
;; 'off' cubes, they add 0 lights, but still get removed from cubes
;; before them.
;;
;; ---
;;
;; These two low level utilities do most of the work to find the
;; overlapping areas of two cuboids.

(defn ^:blog clamp
  "Return the coordinate range `x0` -> `x1` clamped to range `lo` -> `hi`.
  Returns nil in the case of no overlap."
  [[lo hi] [x0 x1]]
  (when (and (<= lo x1) (>= hi x0))
    [(max lo x0) (min hi x1)]))

(defn ^:blog overlap
  "Return the cuboid intersection of `c1` and `c2`, else nil."
  [c1 c2]
  (let [result (map clamp c1 c2)]
    (when (every? identity result) result)))

(defn volume [[[x0 x1] [y0 y1] [z0 z1]]]
  (* (- (inc x1) x0)
     (- (inc y1) y0)
     (- (inc z1) z0)))

;; ^:blog The core of the algorithm is this recursive method to find
;; the volume of lights uniquely represented by this cube by
;; subtracting out the volumes of overlapping cubes in later
;; instructions.  To make sure we do not subtract the same volume
;; twice (or more), we need to descend the entire tree recursively. I
;; borrowed the lazy seq `tails` from Haskell to elegantly get the set
;; of remaining combinations to consider.  I inelegantly use `butlast`
;; to prevent nil checks in destructuring, since the last item of
;; tails is '().

(defn ^:blog unique-volume
  "Return the volume unique to `cuboid`.  Recursively remove volumes
  uniquely held by sub-cuboids from those that overlap `cuboid`."
  [cuboid cuboids]
  (let [relevant-cuboids (keep #(overlap cuboid %) cuboids)]
    (reduce (fn [total [this & subcuboids]]
              (- total (unique-volume this subcuboids)))
            (volume cuboid)
            (butlast (tails relevant-cuboids)))))

(defn solve
  "Returns the count of 'on' lights after executing light commands."
  [cmds]
  (->> (butlast (tails cmds))
       (keep (fn [[[op cube] & cmds]]
               (when (= "on" op)        ; 'off' lights don't contribute to total, skip
                 (unique-volume cube (map second cmds)))))
       (reduce +)))

(defn part-1 [input]
  (->> (map parse-line input)
       (filter (fn [[_ cube]] (= cube (overlap cube (repeat 3 [-50 50])))))
       solve))

(defn part-2 [input]
  (solve (map parse-line input)))
