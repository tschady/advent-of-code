(ns aoc.2023.d02
  (:require
   [aoc.file-util :as f]
   [aoc.matrix :as matrix]
   [clojure.string :as str]))

(def input (f/read-lines "2023/d02.txt"))

(defn parse-game
  "Parses game line into hashmap of id to vector of [r g b] tuples."
  [s]
  (let [[_ id] (re-find #"^Game (\d+):" s)
        pulls  (str/split s #";")]
    (hash-map (parse-long id)
              (vec (for [pull pulls
                         :let [[_ r] (re-find #"(\d+) red" pull)
                               [_ g] (re-find #"(\d+) green" pull)
                               [_ b] (re-find #"(\d+) blue" pull)]]
                     [((fnil parse-long "0") r)
                      ((fnil parse-long "0") g)
                      ((fnil parse-long "0") b)])))))

(defn parse-games [games] (apply merge (map parse-game games)))

(defn possible?
  "Returns true if every [r g b] tuple in `pulls` is < than `max-pull`."
  [max-pull [_ pulls]]
  (every? true? (mapcat #(map >= max-pull %) pulls)))

(defn power
  "Returns the product of the highest pull by color for a given game."
  [[_ pulls]]
  (reduce * (map (partial apply max) (matrix/transpose pulls))))

(defn part-1 [input]
  (->> (parse-games input)
       (filter (partial possible? [12 13 14]))
       (map first)
       (reduce +)))

(defn part-2 [input]
  (->> (parse-games input)
       (map power)
       (reduce +)))
