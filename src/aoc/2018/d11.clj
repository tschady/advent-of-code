(ns aoc.2018.d11
  (:require [clojure.string :as str]))

(def input 9810)

(def matrix-size 300)

(defn- hundredths-digit [n] (-> n (quot 100) (mod 10)))

(defn cell-power
  "Compute the power level for a cell given by [x y] coords and a grid serial number."
  [grid-sn [x y]]
  (let [rack-id (+ x 10)]
    (-> (* y rack-id)
        (+ grid-sn)
        (* rack-id)
        hundredths-digit
        (- 5))))

(defn format-answer [xs] (str/join "," xs))

(defn make-grid-coords
  ""
  [size]
  (for [x (range 1 (inc size))
        y (range 1 (inc size))]
    [x y]))

(defn summed-area
  "Must be called in 'reader' order, left to right, top to bottom, with origin in top left corner."
  [grid-sn m [x y]]
  (let [cur-power (cell-power grid-sn [x y])
        up (get m [x (dec y)] 0)
        left (get m [(dec x) y] 0)
        up-left (get m [(dec x) (dec y)] 0)]
    (- (+ cur-power up left) up-left)))

(defn build-matrix
  [grid-sn size]
  (reduce (fn [m [x y]] (assoc m [x y] (summed-area grid-sn m [x y])))
          {}
          (make-grid-coords size)))

(defn subgrid-power
  ""
  [subgrid-size matrix [x y]]
  (let [delta (dec subgrid-size)
        nw (get matrix [(dec x) (dec y)] 0)
        ne (get matrix [(+ x delta) (dec y)] 0)
        sw (get matrix [(dec x) (+ y delta)] 0)
        se (get matrix [(+ x delta) (+ y delta)])]
    (when-not (nil? se)
      (- (+ se nw) ne sw))))

(defn power-grid
  [matrix subgrid-size]
  (->> (keys matrix)
       (map #(subgrid-power subgrid-size matrix %))
       (zipmap (keys matrix))
       (filter second)
       (into {})))

(defn max-power
  ""
  [matrix subgrid-size]
  (apply max-key val (power-grid matrix subgrid-size)))

(defn part-1
  ""
  [grid-sn]
  (-> (build-matrix grid-sn matrix-size)
      (max-power 3)
      key
      format-answer))

(defn part-2
  ""
  [grid-sn]
  (let [matrix (build-matrix grid-sn matrix-size)]
    (->> (range 1 (inc matrix-size))
         (map (fn [subgrid-size]
                (let [[[x y] p] (max-power matrix subgrid-size)]
                  [[x y subgrid-size] p])))
         (into {})
         (apply max-key val)
         key
         format-answer)))
