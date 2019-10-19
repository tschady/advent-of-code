(ns aoc.2018.d18
  (:require [aoc.file-util :as file-util]
            [aoc.math-util :refer [neighbors]]))

(def input (file-util/read-lines "2018/d18.txt"))

(def glyph->terrain {\. :open
                     \| :trees
                     \# :lumberyard})

(defn- build-parcel
  "Map cartesian coord tuple to terrain, given list of strings of glyphs."
  [lines]
  (apply merge (flatten
                (map-indexed (fn [y row]
                               (map-indexed (fn [x char]
                                              {[x y] (glyph->terrain char)})
                                            row))
                             lines))))

(defn- age-acre
  "Transform an acre based on its surroundings."
  [terrain surrounds]
  (case terrain
    :open (if (> (get surrounds :trees 0) 2)
            :trees
            :open)
    :trees (if (> (get surrounds :lumberyard 0) 2)
             :lumberyard
             :trees)
    :lumberyard (if (and (pos? (get surrounds :lumberyard 0))
                         (pos? (get surrounds :trees 0)))
                  :lumberyard
                  :open)))

(defn- age-parcel
  "Advance time one minute, evolving each acre."
  [parcel]
  (into {} (map (fn [[coord terrain]]
                  [coord
                   (->> coord
                        (neighbors parcel)
                        frequencies
                        (age-acre terrain))])
                parcel)))

(defn- resources-after-time
  "Frequency map of resources for a given `parcel` after time `t`."
  [parcel t]
  (-> (iterate age-parcel parcel)
      (nth t)
      vals
      frequencies))

(defn- cycle-stats
  "Determine the offset and period of a cycle in the sequence of parcels
  over time."
  [init-parcel]
  (loop [parcel init-parcel
         i 0
         seen {}]
    (if (contains? seen parcel)
      (let [offset (seen parcel)
            period (- i offset)]
        [offset period])
      (recur (age-parcel parcel)
             (inc i)
             (assoc seen parcel i)))))

(defn resource-value [{:keys [trees lumberyard]}] (* trees lumberyard))

(defn part-1
  "Returns resource-value of input parcel after t minutes."
  [input t]
  (-> input
      build-parcel
      (resources-after-time t)
      resource-value))

(defn part-2
  "Returns resource-value of input parcel after t minutes."
  [input t]
  (let [parcel (build-parcel input)
        [offset period] (cycle-stats parcel)
        iter (+ offset (mod (- t offset) period))
        resources (resources-after-time parcel iter)]
    (resource-value resources)))
