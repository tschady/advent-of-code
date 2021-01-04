(ns aoc.2020.d22
  (:require [instaparse.core :as insta]
            [aoc.file-util :as file-util]))

(def input (file-util/read-file "2020/d22.txt"))

(def grammar "
<S> = player <NL NL> player
player = <'Player ' #'[0-9]+' ':' NL> (card)+
card = #'[0-9]+' <(NL)?>
NL = '\n'
")

(defn hashstate [p1 p2] (str p1 p2))

(defn play-subgame? [p1 p2]
  (and (< (first p1) (count p1))
       (< (first p2) (count p2))))

(defn p1-highcard? [p1 p2]
  (> (first p1) (first p2)))

(defn subgame-deck [p] (subvec p 1 (inc (first p))))

(defn winning-deck [p1 p2 p1-wins?]
  (loop [p1   p1
         p2   p2
         seen #{}]
    (let [hash       (hashstate p1 p2)
          duplicate? (contains? seen hash)]
      (cond
        duplicate?  {:winner :p1 :deck p1}
        (empty? p1) {:winner :p2 :deck p2}
        (empty? p2) {:winner :p1 :deck p1}

        (p1-wins? p1 p2)
        (recur (conj (subvec p1 1) (first p1) (first p2))
               (subvec p2 1)
               (conj seen hash))

        :else
        (recur (subvec p1 1)
               (conj (subvec p2 1) (first p2) (first p1))
               (conj seen hash))))))

(defn p1-wins-recursive? [p1 p2]
  (if (play-subgame? p1 p2)
    (= :p1 (:winner (winning-deck (subgame-deck p1)
                                  (subgame-deck p2)
                                  p1-wins-recursive?)))
    (p1-highcard? p1 p2)))

(defn deck-score [deck]
  (->> (reverse deck)
       (map-indexed #(* (inc %1) %2))
       (reduce + 0)))

(defn solve [input win-fn]
  (let [[p1 p2] (->> ((insta/parser grammar) input)
                     (insta/transform {:card #(Integer/parseInt %)
                                       :player vector}))]
    (deck-score (:deck (winning-deck p1 p2 win-fn)))))

(defn part-1 [input] (solve input p1-highcard?))

(defn part-2 [input] (solve input p1-wins-recursive?))
