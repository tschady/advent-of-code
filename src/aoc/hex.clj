(ns aoc.hex
  (:require [aoc.grid :as grid]))

;; good resource: https://www.redblobgames.com/grids/hexagons/
;;   useful to complete the alternate coordinate schemes besides
;;   `:odd-r`

(def dir->delta
  {:odd-r {:even {"w"  [-1 0]
                  "e"  [1 0]
                  "nw" [-1 -1]
                  "ne" [0 -1]
                  "sw" [-1 1]
                  "se" [0 1]}
           :odd  {"w"  [-1 0]
                  "e"  [1 0]
                  "nw" [0 -1]
                  "ne" [1 -1]
                  "sw" [0 1]
                  "se" [1 1]}}})

;; todo: using `x` or `y` for evenness depends on `-r` or `-q` coord scheme.
;;   update if we ever use `-q`
(defn delta
  ([loc] (delta loc :odd-r))
  ([[x y] system]
   (let [evenness (if (even? y) :even :odd)]
     (get-in dir->delta [system evenness]))))

(defn neighbor-deltas
  ([loc] (neighbor-deltas loc :odd-r))
  ([loc system] (vals (delta loc system))))

(defn neighbors
  ([loc] (neighbors loc :odd-r))
  ([loc system] (map (partial grid/vector-add loc) (neighbor-deltas loc system))))

