(ns aoc.elfscript
  (:require [clojure.string :as str]))

(declare char-4x6)

(defn- transpose [rows] (apply map list rows))

(defn- normalize [screen {:keys [off on]}]
  (->> screen
       (map #(str/replace % off \.))
       (map #(str/replace % on \#))))

(defn ocr
  ([screen glyphs] (ocr (normalize screen glyphs)))
  ([screen]
   (->> (transpose screen)
        (partition-all 5) ; parse out each letter with space after
        (map (partial take 4)) ; remove space
        (map transpose)
        (map #(map (partial apply str) %))
        (map char-4x6)
        (apply str))))

(def char-4x6
  {'(".##."
     "#..#"
     "#..#"
     "####"
     "#..#"
     "#..#") \A

   '("###."
     "#..#"
     "###."
     "#..#"
     "#..#"
     "###.") \B

   '(".##."
     "#..#"
     "#..."
     "#..."
     "#..#"
     ".##.") \C

   '("####"
     "#..."
     "###."
     "#..."
     "#..."
     "####") \E

   '("####"
     "#..."
     "###."
     "#..."
     "#..."
     "#...") \F

   '(".##."
     "#..#"
     "#..."
     "#.##"
     "#..#"
     ".###") \G

   '("#..#"
     "#..#"
     "####"
     "#..#"
     "#..#"
     "#..#") \H

   '("..##"
     "...#"
     "...#"
     "...#"
     "#..#"
     ".##.") \J

   '("#..#"
     "#.#."
     "##.."
     "#.#."
     "#.#."
     "#..#") \K

   '("#..."
     "#..."
     "#..."
     "#..."
     "#..."
     "####") \L

   '("###."
     "#..#"
     "#..#"
     "###."
     "#..."
     "#...") \P

   '("###."
     "#..#"
     "#..#"
     "###."
     "#.#."
     "#..#") \R

   '("#..#"
     "#..#"
     "#..#"
     "#..#"
     "#..#"
     ".##.") \U

   '("####"
     "...#"
     "..#."
     ".#.."
     "#..."
     "####") \Z})
