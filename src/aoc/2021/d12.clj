(ns aoc.2021.d12
  (:require
   [aoc.file-util :as file-util]
   [clojure.string :as str]))

(def input (file-util/read-lines "2021/d12.txt"))

(defn parse-graph [lines]
  (apply merge-with into
         (for [line lines
               :let [[a b] (str/split line #"-")]]
           {a [b], b [a]})))

(defn small-cave? [s] (Character/isLowerCase (first s)))

;; ^:blog
;; This problem immediately looked like a recursive Depth First Search.
;; For part-1 I could track the typical visited nodes and remove them from
;; the next search level, but interestingly part-2 flipped this on its head
;; and used a variable count.  This could be tracked with an extra boolean
;; like `bonus-used?`, but I preferred to put this complexity in the data layer
;; with the allowances map below.

(defn ^:blog dfs-paths [g goal path allowances]
  (let [curr (peek path)]
    (if (= goal curr)
      (vector path)
      (let [nexts (filter #(pos? (get allowances %)) (get g curr))]
        (mapcat #(dfs-paths g goal (conj path %) (update allowances curr dec)) nexts)))))

;; ^:blog
;; The interesting part of the algorithm is this map of the number of times
;; each cave may be visited.  I use infinity again for large cave count since it can be decremented forever.

(defn ^:blog make-allowances
  "Returns map of cave to number of times it may be visited.
  Small caves begin with lowercase and can be visited once.
  Large caves (everything not small) can be visited infinitely."
  [g]
  (let [{small true, big false} (group-by small-cave? (keys g))]
    (merge (zipmap small (repeat 1)) (zipmap big (repeat ##Inf)))))

(defn part-1 [input]
  (let [g     (parse-graph input)
        allow (make-allowances g)]
    (count (dfs-paths g "end" ["start"] allow))))

;; ^:blog
;; I optimized for dev time and DFS algo simplicity here, by iterating over
;; the collection of small-caves, treating each one in turn as
;; the magic cave that can be visited twice.  There is a lot of duplication here,
;; with many paths visited multiple times then collpased with `set`.

(defn ^:blog part-2 [input]
  (let [g           (parse-graph input)
        init-allow  (make-allowances g)
        small-caves (remove #{"start" "end"} (filter small-cave? (keys g)))]
    (->> small-caves
         (map #(update init-allow % inc))
         (mapcat (partial dfs-paths g "end" ["start"]))
         set
         count)))
