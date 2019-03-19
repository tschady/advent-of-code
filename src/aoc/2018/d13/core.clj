(ns aoc.2018.d13.core
  (:require [aoc.file-util :as file-util]))

(def input (file-util/read-lines "2018/d13.txt"))


(def cart-map {\> {:underlying-track \-
                   :move [1 0]
                   :corner->turn {\/ \^,
                                  \\ \v}}
               \< {:underlying-track \-
                   :move [-1 0]
                   :corner->turn {\/ \v,
                                  \\ \^}}
               \^ {:underlying-track \|
                   :move [0 -1]
                   :corner->turn {\/ \>,
                                  \\ \<}}
               \v {:underlying-track \|
                   :move [0 1]
                   :corner->turn {\/ \<,
                                  \\ \>}}})

(defn cart? [c] (contains? cart-map c))

(defn- make-cart
  ""
  [[x y] c]
  (sorted-map [x y] {:dir c :turns (cycle [:left :straight :right])}))

(defn- make-world
  "Return a world structure, which is a map of three elements:
  :track
  :carts
  :crashes
  "
  [input]
  (apply merge-with into (for [y (range (count input))
                               x (range (count (get input y)))]
                           (let [c (get-in input [y x])
                                 cart (if (cart? c)
                                        (make-cart [x y] c)
                                        {})
                                 track (if (not-empty cart)
                                         (get-in cart-map [c :underlying-track])
                                         c)]
                             {:track (sorted-map [x y] track)
                              :carts cart
                              :crashes []}))))


(defn update-world
  "Tick time once in world.  Carts move, turn, and sometimes crash.
  Return new world object one time-step later."
  [world]
  (reduce (fn [acc cart]
            (let )
            )
          world
          (:carts world)))

;;;;;;;;;;;;;;;;;;;;;;;;;

(def ti ["/->-\\"
         "|   |  /----\\"
         "| /-+--+-\\  |"
         "| | |  | v  |"
         "\\-+-/  \\-+--/"
         "\\------/"])

(make-world ti)

(def d (make-cart [0 0] \>))
d

(first (get-in d [[0 0] :turns]))
(update-in d [[0 0] :turns] rest)
