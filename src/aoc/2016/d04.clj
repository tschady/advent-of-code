(ns aoc.2016.d04
  (:require [clojure.string :as str]
            [aoc.file-util :as file-util]))

(def input (file-util/read-lines "2016/d04.txt"))

(defn parse-room [s]
  (let [[_ raw-code raw-id checksum] (re-matches #"([-a-z]+)-(\d+)\[(.*?)\]" s)
        id (read-string raw-id)
        code (str/replace raw-code "-" "")]
    {:id id :code code :sum checksum}))

(defn checksum [code]
  (->> code
       frequencies
       (sort #(compare [(val %2) (key %1)]
                       [(val %1) (key %2)]))
       (take 5)
       (map first)
       (apply str)))

(defn valid-room? [room]
  (= (checksum (:code room)) (:sum room)))

(defn shift-letter [n c]
  (-> (int c)
      (- (int \a))
      (+ n)
      (mod 26)
      (+ (int \a))
      char))

(defn decrypt [room]
  (let [cleartext (->> (:code room)
                       (map (partial shift-letter (:id room)))
                       (apply str))]
    (assoc room :clear cleartext)))

(defn part-1 [input]
  (->> input
       (map parse-room)
       (filter valid-room?)
       (map :id)
       (reduce + 0)))

(defn part-2 [input]
  (->> input
       (map parse-room)
       (filter valid-room?)
       (map decrypt)
       (filter #(str/includes? (:clear %) "northpole"))
       first
       :id))
