(ns aoc-2017.d03)

(def input 368078)

(defn- num->ring
  "Given a value `n` in the spiral, determine in which outward ring it occurs.
  The ring count is zero based."
  [n]
  (-> n Math/sqrt dec (* 1/2) Math/ceil int))

(defn- ring->max-val
  "Given a ring `n` in the spiral, compute the maximum value in that ring."
  [n]
  (-> n (* 2) inc (Math/pow 2) int))

(defn- ring->cardinal-vals
  "Given a ring `n`, return the 4 values at the cardinal directions."
  [n]
  (->> (range 4)
       (map #(* 2 n %))
       (map #(+ n (ring->max-val (dec n)) %))))

(defn- dist-to-cardinal
  "Find the distance of the target val `n` from the nearest cardinal value
  of that ring."
  [n cardinals]
  (->> cardinals
       (map #(- n %))
       (map #(Math/abs %))
       (apply min)))

(defn part-1
  "The Manhattan distance of the value `n` from the center of the spiral
  is the sum of which outer ring the value is on, plus the distance from
  the nearest cardinal direction.  O(1) solution."
  [n]
  (let [ring (num->ring n)
        cardinals (ring->cardinal-vals ring)]
    (+ ring (dist-to-cardinal n cardinals))))
