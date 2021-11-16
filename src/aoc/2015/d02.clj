(ns aoc.2015.d02
  (:require [aoc.file-util :as file-util]
            [aoc.string-util :as string-util]))

(def input (file-util/read-lines "2015/d02.txt"))

(defn paper-needed
  "Return paper area required to wrap box in sq. ft.
  Equal to the areas of all 6 sides plus the area of the smallest side."
  [[x y z]]
  (let [s1 (* x y)
        s2 (* y z)
        s3 (* x z)
        extra (min s1 s2 s3)]
    (+ extra (* 2 (+ s1 s2 s3)))))

(defn ribbon-needed
  "Return ribbon required to wrap box in sq. ft.
  Equal to the smallest rectangular circumpherence plus volume."
  [[x y z :as dims]]
  (let [bow (* x y z)
        [a b _] (sort dims)]
    (+ bow a a b b)))

(defn part-1
  "Return total area of paper required to wrap all input gifts."
  [gifts]
  (->> gifts
       (map string-util/ints)
       (map paper-needed)
       (reduce + 0)))

(defn part-2
  "Return length of ribbon required to wrap all input gifts."
  [gifts]
  (->> gifts
       (map string-util/ints)
       (map ribbon-needed)
       (reduce + 0)))
