(ns aoc.2015.d11
  (:require [clojure.spec.alpha :as s]
            [clojure.string :as str]))

(def input "hepxcrrq")

(defn pass->int
  "Convert string password to equivalent base 26 form."
  [s]
  (let [normal-num (->> s
                        (map #(char (- (int %) (if (<= (int %) (int \j)) 49 10))))
                        str/join)]
    (Long/parseLong normal-num 26)))

(defn int->pass
  "Convert base 26 representation of password to equivalent string form."
  [n]
  (->> (Long/toString n 26)
       (map #(char (+ (int %) (if (<= (int %) (int \9)) 49 10))))
       (apply str)))

(defn inc-pass
  "Given a password, return the next 'increment' password."
  [pass]
  (-> pass pass->int inc int->pass))

(def straight
  "Regex pattern matching any 3-letter consecutive runs in alphabet"
  (->> "abcdefghijklmnopqrstuvwxyz"
       (partition 3 1)
       (map str/join)
       (str/join "|")
       re-pattern))

(s/def ::password (s/and string?
                         #(re-find #"(.)\1.*(.)(?!\1)\2" %) ; diff double-repeat
                         #(not (re-find #"[iol]" %)) ; no 'iol'
                         #(re-find straight %)))

(def valid-passwords (filter #(s/valid? ::password %) (iterate inc-pass input)))

(defn part-1 [] (first valid-passwords))
(defn part-2 [] (second valid-passwords))
