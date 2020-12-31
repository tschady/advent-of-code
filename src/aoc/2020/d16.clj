(ns aoc.2020.d16
  (:require
   [aoc.file-util :as file-util]
   [aoc.string-util :as string-util]
   [clojure.set :as set]
   [clojure.string :as str]))

(def input (file-util/read-file "2020/d16.txt"))

(defn- parse-rules
  "Returns a map of rule name to set of ints allowed."
  [rules]
  (apply merge
   (for [rule rules
         :let [domain (->> rule
                           string-util/ints-pos
                           (partition 2)
                           (mapcat #(range (first %) (inc (second %))))
                           set)
               field   (second (re-find #"^(.*):" rule))]]
     {field domain})))

(defn parse-input
  "Returns a map of:
  - rules (rule name -> set of valid numbers)
  - your ticket (vector of numbers)
  - nearby tickets (vector of vector of numbers"
  [s]
  (let [[raw-rules raw-mine raw-nearby] (mapv str/split-lines (str/split s #"\n\n"))]
    {:rules (parse-rules raw-rules)
     :mine (string-util/ints (last raw-mine))
     :nearby (mapv string-util/ints (rest raw-nearby))}))

(defn possible-vals [rules]
  (apply set/union (vals rules)))

(defn part-1 [input]
  (let [{rules :rules, nearby :nearby} (parse-input input)
        valids (possible-vals rules)]
    (->> nearby
         (mapcat (partial remove valids))
         (reduce +))))

(defn fitting-rules [rules col]
  (->> rules
       (keep (fn [[k v]] (when (every? v col) k)))))

(defn quick-solve [db]
  (loop [db     (sort-by #(count (val %)) (zipmap (range) db))
         used   #{}
         result {}]
    (if (empty? db)
      result
      (let [item    (first db)
            choices (remove used (second item))]
        (if (= 1 (count choices))
          (recur (rest db)
                 (conj used (first choices))
                 (assoc result (first item) (first choices)))
          "This dataset too complex for quick-solve.")))))

(defn part-2 [input]
  (let [{rules  :rules,
         mine   :mine,
         nearby :nearby} (parse-input input)
        valid-nearby     (filter #(every? (possible-vals rules) %) nearby)
        all-tix          (conj valid-nearby mine)
        cols             (apply mapv vector all-tix)
        quick-poss       (map (partial fitting-rules rules) cols)]
    (->> (quick-solve quick-poss)
         (filter #(str/starts-with? (val %) "departure"))
         (map first)
         (map #(nth mine %))
         (reduce *))))
