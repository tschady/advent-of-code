(ns aoc.2018.d13.core
  (:require [aoc.file-util :as file-util]))

(def input (file-util/read-lines "2018/d13.txt"))


(def cart-map {\> {:track \-
                   :dir :e
                   :move [1 0]}
               \< {:track \-
                   :dir :w
                   :move [-1 0]}
               \^ {:track \|
                   :dir :n
                   :move [0 -1]}
               \v {:track \|
                   :dir :s
                   :move [0 1]}})

(defn cart? [c] (contains? cart-map c))

(def ti ["/->-\\"
         "|   |  /----\\"
         "| /-+--+-\\  |"
         "| | |  | v  |"
         "\\-+-/  \\-+--/"
         "\\------/"]
)


(defn- make-cart
  ""
  [[x y] c]
  (let [dir (get-in cart-map [c :dir])]
    (sorted-map [x y] {:dir dir :turns (cycle [:left :straight :right])})))

(defn- build-world
  "Return a world structure, which is a map of three elements:
  :carts
  :track
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
                                         (get-in cart-map [c :track])
                                         c)]
                             {:track (sorted-map [x y] track)
                              :cart cart}))))

(build-world ti)

(def d (make-cart [0 0] \>))
d

(get-in d [[0 0] :turns])

(update-in d [[0 0] :turns] #(drop 1 %))
