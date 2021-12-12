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

(defn dfs-paths [g goal path allowances]
  (let [curr (peek path)]
    (if (= goal curr)
      (vector path)
      (let [nexts (filter #(pos? (get allowances %)) (get g curr))]
        (mapcat #(dfs-paths g goal (conj path %) (update allowances curr dec)) nexts)))))

(defn make-allowances
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

;; Iterate over the collection of small-caves, treating each one in turn as
;; the magic cave that can be visited twice.  Must use set to avoid
;; duplicate paths (where small caves aren't needed in the solution.)
(defn part-2 [input]
  (let [g           (parse-graph input)
        init-allow  (make-allowances g)
        small-caves (remove #{"start" "end"} (filter small-cave? (keys g)))]
    (->> small-caves
         (map #(update init-allow % inc))
         (mapcat (partial dfs-paths g "end" ["start"]))
         set
         count)))
