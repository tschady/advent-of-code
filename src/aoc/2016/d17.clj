(ns aoc.2016.d17
  (:require
   [aoc.file-util :as file-util]
   [aoc.grid :as grid]
   [clojure.core.matrix :as m]
   [clojure.data.priority-map :refer [priority-map]]
   [digest :refer [md5]]))

(def input (file-util/read-file "2016/d17.txt"))

(defn- unlocked? [c] (some #{c} "bcdef"))

(def dirs [[\U [0 -1]]
           [\D [0 1]]
           [\L [-1 0]]
           [\R [1 0]]])

(defn- in-bounds? [pos size] (every? true? (map #(<= 0 % %2) pos size)))

(defn next-states [{:keys [pos path]} goal size pass]
  (let [doors (take 4 (md5 (str pass (apply str path))))
        moves (keep-indexed #(when (unlocked? %2) (dirs %1)) doors)]
    (for [[dir step] moves
          :let [new-pos (m/add pos step)
                dist (grid/manhattan-dist new-pos goal)]
          :when (in-bounds? new-pos size)]
      {{:pos new-pos :path (conj path dir)} (+ dist (inc (count path)))})))

(defn solve-astar [start goal size pass]
  (loop [p (priority-map {:pos start :path []} (grid/manhattan-dist start goal))]
    (let [curr (first (peek p))]
      (cond
        (nil? curr)          :no-solution
        (= (:pos curr) goal) (apply str (:path curr))
        :else
        (recur (into (pop p) (next-states curr goal size pass)))))))

;; This is made more complicated by `next-states` having the A*
;; heuristic calc.  I could de-dupe this problem by solving all paths,
;; then taking the shortest or the longest, but I already had the A*
;; in anticipation of a gigantic part2.
(defn solve-longest [start goal size pass]
  (loop [q         [{{:pos start :path []} 0}]
         solutions []]
    (let [curr (first (keys (peek q)))]
      (cond
        (empty? q) (apply max solutions)

        (= (:pos curr) goal)
        (recur (pop q)
               (conj solutions (count (:path curr))))

        :else
        (recur (into (pop q) (next-states curr goal size pass))
               solutions)))))

(defn part-1 [pass] (solve-astar [0 0] [3 3] [3 3] pass))

(defn part-2 [pass] (solve-longest [0 0] [3 3] [3 3] pass))
