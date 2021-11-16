(ns aoc.2018.d03
  (:require
   [aoc.file-util :as file-util]
   [aoc.string-util :as string-util]
   [clojure.set :as set]))

(def input (file-util/read-lines "2018/d03.txt"))

(defn- parse-claim
  "Given an input claim string, parse into hashmap"
  [claim-str]
  (zipmap [:id :x :y :dx :dy] (string-util/ints claim-str)))

(defn- claim-locs
  "Return all the Cartesian coords for the area of a claim"
  [claim]
  (for [dx (range (:dx claim))
        dy (range (:dy claim))]
    [(+ (:x claim) dx) (+ (:y claim) dy)]))

(defn- add-claim-to-fabric
  "Reduction function to add claim to existing fabric."
  [fabric claim]
  (let [locs (claim-locs claim)
        locs->id (zipmap locs (repeat (conj #{} (:id claim))))]
    (merge-with into fabric locs->id)))

(defn- build-fabric
  "Create fabric from collection of claims.  Fabric is a hashmap of
  [x y] location to the set of all claim ids that occupy that location."
  [raw-claims]
  (->> raw-claims
       (map parse-claim)
       (reduce add-claim-to-fabric {})))

(defn- overlapping-squares
  "Return subset of fabric with overlapping claims."
  [fabric]
  (into {} (filter (fn [[_ v]] (< 1 (count v))) fabric)))

(defn part-1
  "Return number of squares of fabric within 2 or more claims."
  [claims]
  (-> claims build-fabric overlapping-squares count))

(defn- get-fabric-ids
  "For given fabric section, return all claim ids occurring within."
  [fabric]
  (apply set/union (vals fabric)))

(defn part-2
  "Return first claim which does not overlap with any other."
  [claims]
  (let [fabric (build-fabric claims)
        all-ids (get-fabric-ids fabric)
        overlap-ids (get-fabric-ids (overlapping-squares fabric))]
    (first (set/difference all-ids overlap-ids))))
