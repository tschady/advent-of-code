(ns aoc.2020.d20
  (:require
   [aoc.file-util :as file-util]
   [aoc.matrix :as matrix]
   [clojure.string :as str]
   [com.rpl.specter :as specter :refer [ALL MAP-VALS select transform]]))

(def input (file-util/read-chunks "2020/d20.txt"))

(defn digitize
  "Turn a string into a decimal number by binary evaluation of '#' as 1, and '.' as 0."
  [line]
  (Long/parseLong (apply str (replace {\# \1 \. \0} line)) 2))

(defn hashed-tile
  "Return a hashmap of a tile, with digitized edges for comparison to neighbors."
  [id body]
  {:id id
   :body body
   :edges {:t (digitize (first body))
           :b (digitize (last body))
           :l (digitize (map first body))
           :r (digitize (map last body))}})

(defn rotations
  ;; TODO use this in parse-tile and final problem.  Problems w/ use of seqs and strings in diff fns
  "Return the 8 possible orientations of a 2D string using rotations and flips."
  [s]
  (let [rots (->> (map seq s)
                  (iterate matrix/rot-r)
                  (take 4))
        flips (map matrix/flip-x rots)]
    (->> (concat rots flips)
         (map #(mapv (partial apply str) %)))))

(defn parse-tile
  "Return collection of hashmaps of tiles of every possible position."
  [chunk]
  (let [[title & body] (-> chunk (str/split #"\n"))
        id             (Long/parseLong (re-find #"\d+" title))
        rots           (->> (map seq body)
                            (iterate matrix/rot-r)
                            (take 4))
        flip-x         (map matrix/flip-x rots)]
    (map #(hashed-tile id %) (concat rots flip-x))))

(defn freq-db [variants]
  (->> variants
       (select [ALL :edges MAP-VALS])
       frequencies))

(defn find-corner-ids
  "Returns a coll of the ids of the 4 corner tiles.  Corner tiles must have the fewest
  possible connections (2) of all the tiles.  We compute by finding the frequency of
  all edge scores, summing, and taking the smallest 4."
  [variants]
  (let [freq-db (freq-db variants)]
    (->> variants
         (transform [ALL :edges MAP-VALS] #(get freq-db %))
         (sort-by #(reduce + (select [:edges MAP-VALS] %)))
         (map :id)
         (dedupe)
         (take 4))))

(defn part-1 [input]
  (->> (mapcat parse-tile input)
       find-corner-ids
       (reduce *)))

(defn top-left-corner
  "Return a tile variant with id `corner-id` so that it's connections are right and down."
  [corner-id variants]
  (let [freq-db (freq-db variants)]
    (->> variants
         (filter (comp #{corner-id} :id))
         (filter #(= 8
                     (get freq-db (get-in % [:edges :b]))
                     (get freq-db (get-in % [:edges :r]))))
         first)))

(defn remove-edges
  "Return the input tile body with its outer edges removed."
  [body]
  (->> body rest butlast (map rest) (map butlast)))

(defn assemble-image
  "Return the ASCII image of the solved puzzle as an array of strings."
  [puzzle]
  (let [puzzle (flatten puzzle) ; kludge: we originally had array, not grid
        dim (-> puzzle count Math/sqrt int)]
    (->> puzzle
         (map :body)
         (map remove-edges)
         (map #(map (partial apply str) %))
         (partition dim)
         (mapcat #(apply map str %))
         vec)))

(defn neighbor
  "Return tile whose `theirs` edge matches the `ours` edge of `tile`."
  [variants [ours theirs] tile]
  (->> variants
       (filter #(== (get-in tile [:edges ours]) (get-in % [:edges theirs])))
       (remove #(== (:id tile) (:id %)))
       first))

(defn solve-puzzle
  "Find the square arrangement of puzzle pieces given by `input` that assemble with correct
  adjacent edges.  This depends on having only 4 possible corners, and only 1 possible
  solution (aside from rotations).  Since there is only 1 solution possible, we choose 1
  corner, then iterate to find all tiles down from there, representing the head of each row.
  From here, we can iterate to find all tiles to the right of each row header."
  [input]
  (let [variants  (mapcat parse-tile input)
        dim       (int (Math/sqrt (/ (count variants) 8)))
        top-left  (-> variants find-corner-ids first (top-left-corner variants))
        left-side (take dim (iterate (partial neighbor variants [:b :t]) top-left))]
    (->> left-side
         (map #(take dim (iterate (partial neighbor variants [:r :l]) %))))))

(def monster
  ["                  # "
   "#    ##    ##    ###"
   " #  #  #  #  #  #   "])

(def monster-coords
  "The relative x,y coordinates for every monster piece"
  (for [y     (range (count monster))
        x     (range (count (first monster)))
        :when (= \# (get-in monster [y x]))]
    [x y]))

(defn- count-hashes
  "Return number of pound-signs in given 2D string `s`."
  [s]
  (get (frequencies (apply str s)) \#))

(defn monster-at? [image [x y]]
  (->> monster-coords
       (map (fn [[dx dy]] (get-in image [(+ dy y) (+ dx x)] nil)))
       (every? #(= \# %))))

(defn monster-count
  "Returns the number of occurrences of the `monster` pattern in the ASCII-art `img`."
  [img]
  (reduce + (for [y     (range (count img))
                  x     (range (count (first img)))
                  :when (monster-at? img [x y])]
            1)))

(defn part-2 [input]
  (let [img (-> input solve-puzzle assemble-image)
        num-monsters (->> (rotations img)
                          (map monster-count)
                          (apply max))]
    (- (count-hashes img) (* num-monsters (count-hashes monster)))))
