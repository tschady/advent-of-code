(ns aoc.elfscript
  (:require
   [clojure.string :as str]))

(declare fonts)

(defn- transpose [rows] (apply map list rows))

(defn- normalize [screen {:keys [off on]}]
  (->> screen
       (map #(str/replace % off \.))
       (map #(str/replace % on \#))))

(defn ocr
  "Returns the string representation of the characters on `screen`,
  which is an array of rows of pixel outputs.  Default pixels are '#'
  for on and '.' for off, but these chars may be given in optional
  {:on c :off c} argument."
  ([screen glyphs] (ocr (normalize screen glyphs)))
  ([screen]
   (let [height (count screen)
         font (get fonts height)
         width (:width font)
         padding (:padding font)
         letters (:letters font)]
     (->> (transpose screen)
          (partition-all (+ width padding))  ; chop letters with space after
          (map (partial take width))   ; trim letter
          (map transpose)
          (map #(map (partial apply str) %))
          (map #(or (letters %)
                    (throw (Exception.
                            (format "Unknown character: '%s'" (str/join "\n" %))))))
          (apply str)))))

(def fonts
  {6 {:width 4
      :padding 1
      :letters {'(".##."
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
                  "####") \Z}}

   10 {:width   6
       :padding 2
       :letters {'("..##.."
                   ".#..#."
                   "#....#"
                   "#....#"
                   "#....#"
                   "######"
                   "#....#"
                   "#....#"
                   "#....#"
                   "#....#") \A

                 '("######"
                   "#....."
                   "#....."
                   "#....."
                   "#####."
                   "#....."
                   "#....."
                   "#....."
                   "#....."
                   "#.....") \F

                 '("#....#"
                   "#....#"
                   "#....#"
                   "#....#"
                   "######"
                   "#....#"
                   "#....#"
                   "#....#"
                   "#....#"
                   "#....#") \H

                 '("...###"
                   "....#."
                   "....#."
                   "....#."
                   "....#."
                   "....#."
                   "....#."
                   "#...#."
                   "#...#."
                   ".###..") \J

                 '("#....#"
                   "#...#."
                   "#..#.."
                   "#.#..."
                   "##...."
                   "##...."
                   "#.#..."
                   "#..#.."
                   "#...#."
                   "#....#") \K}}})
