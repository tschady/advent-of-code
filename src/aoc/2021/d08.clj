(ns aoc.2021.d08
  (:require
   [aoc.file-util :as file]
   [aoc.string-util :refer [diff alphagram]]
   [clojure.string :as str]))

(def input (file/read-lines "2021/d08.txt"))

(defn parse-line [s] (partition-all 10 (re-seq #"\w+" s)))

(defn part-1 [input]
  (->> input
       (map parse-line)
       (mapcat second)
       (map count)
       (filter #{2 4 3 7})
       count))

(defn key-for-val [m v] (ffirst (filter (comp #(= v %) val) m)))

(def all ["abcefg", "cf", "acdeg", "acdfg", "bcdf",
          "abdfg", "abdefg", "acf", "abcdefg", "abcdfg"])

;; ^:blog
;; For this problem I used a combination of expected frequencies of the whole,
;; and differences between the characters.
;;
;; - we recognize 1,4,7,8 from their unique string sizes
;; - we know {b e f} from their unique freqs in the full set of numbers
;; - we know {a} from diff of 1 and 7
;; - we know {c} because it's the unknown remaining in 1
;; - we know {d} because it's the only unknown left in 4
;; - we know {g} because it's last
;;
;; I also wrote a handy string diff function that returns a 3-tuple of [only left, only right, common] modeled after `core.data/diff`

(defn ^:blog determine-output [[digits outputs]]
  (let [[one seven four & _] (sort-by count digits)
        all-freq (frequencies (apply str digits))
        b (key-for-val all-freq 6)
        e (key-for-val all-freq 4)
        f (key-for-val all-freq 9)
        a (ffirst (diff seven one))
        c (ffirst (diff one #{f}))
        d (ffirst (diff four #{b c f}))
        g (ffirst (diff "abcdefg" #{a b c d e f}))
        subst-map {a \a b \b c \c d \d e \e f \f g \g}]
    (->> outputs
         (map (comp alphagram (partial replace subst-map)))
         (map #(.indexOf all %))
         str/join
         Long/parseLong)))

(defn part-2 [input]
  (->> input
       (map parse-line)
       (map determine-output)
       (reduce +)))
