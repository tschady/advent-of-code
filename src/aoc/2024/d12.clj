(ns aoc.2024.d12
  (:require
   [aoc.file-util :as f]
   [aoc.grid :as grid]
   [clojure.math.combinatorics :as combo]
   [clojure.set :as set]))

(def input (f/read-lines "2024/d12.txt"))

(defn corner [loc pair]
  (let [ret (mapv - (apply mapv + pair) loc)]
    (when (not= ret loc) ret)))

;; Number of sides of a polygon are the same as number of interior angles
(defn count-angles
  "Return the number of interior angles bordering this block."
  [g loc adj]
  (let [corners (keep (partial corner loc) (combo/combinations adj 2))
        full-corners (filter #(= (get g loc) (get g %)) corners)]
    (condp = (count adj)
      0 4
      1 2
      2 (if (= 0 (count corners))
          0                             ; adjacents are in a line
          (- 2 (count full-corners)))
      3 (- 2 (count full-corners))
      4 (- 4 (count full-corners)))))

(defn flood-fill-region [g loc]
  (loop [todo   [loc]
         region {}]
    (if (empty? todo)
      region
      (let [curr    (peek todo)
            letter  (get g curr)
            adj     (filter #(= letter (get g %)) (grid/neighbor-coords-news curr))
            new-adj (remove #(get region %) adj)
            perim   (- 4 (count adj))
            angles  (count-angles g curr adj)]
        (recur (apply conj (pop todo) new-adj)
               (conj region {curr {:perim perim :sides angles}}))))))

(defn get-regions [g]
  (loop [todo (set (keys g))
         regions []]
    (if (empty? todo)
      regions
      (let [region (flood-fill-region g (first todo))]
        (recur (set/difference todo (keys region))
               (conj regions region))))))

(defn price [k region]
  (* (count region) (reduce + (map k (vals region)))))

(defn solve [input k]
  (->> (grid/build-grid input identity)
       get-regions
       (map (partial price k))
       (reduce +)))

(defn part-1 [input] (solve input :perim))

(defn part-2 [input] (solve input :sides))
