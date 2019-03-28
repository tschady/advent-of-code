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

(defn- cart? [c] (contains? cart-map c))
(defn- corner? [c] (contains? (-> cart-map first val :corner->turn keys set) c))
(defn- intersection? [c] (= c \+))

(defn turn-cart
  ""
  [dir turn]
  (case [dir turn]
    [\< :left] \v
    [\v :left] \>
    [\> :left] \^
    [\^ :left] \<
    [\< :right] \^
    [\v :right] \<
    [\> :right] \v
    [\^ :right] \>
    dir))

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

(defn- update-cart
  ""
  [cart track]
  (let [pos (key cart)
        delta (get-in cart-map [(:dir (val cart)) :move])
        new-pos (mapv + pos delta)
        new-cart (sorted-map new-pos (val cart))
        new-segment (track new-pos)
        next-turn (-> cart val :turns first)]
    (cond
      (corner? new-segment)
      (update-in new-cart [new-pos :dir] #((:corner->turn (cart-map %)) new-segment))

      (intersection? new-segment)
      (-> new-cart
          (update-in [new-pos :dir] turn-cart next-turn)
          (update-in [new-pos :turns] rest))

      :else new-cart)))

(defn update-world
  "Tick time once in world.  Carts move, turn, and sometimes crash.
  Return new world object one time-step later."
  [world]
  (reduce (fn [acc cart]
            (let [new-cart (update-cart cart (:track acc))
                  old-pos (key cart)
                  new-pos (key (first new-cart))
                  objects (set (conj (:crashes acc) (keys (:carts acc))))
                  old-crash? (contains? (:crashes acc) old-pos)
                  new-crash? (contains? objects new-pos)]
              (cond
                old-crash?
                acc

                new-crash?
                (update acc :crashes conj new-pos)

                :else
                (update acc :carts conj new-cart))))
          (dissoc world :carts)
          (:carts world)))

;;;;;;;;;;;;;;;;;;;;;;;;;

(def ti ["/->-\\"
         "|   |  /----\\"
         "| /-+--+-\\  |"
         "| | |  | v  |"
         "\\-+-/  \\-+--/"
         "\\------/"])

(def w (make-world ti))

(update-world w)

(type (first (:carts w)))

(def t (:track w))

(def c (first (:carts w)))
c

(def c2 (update-cart c t))
(key (first c2))



(defn print-cart
  ""
  [{} cart]
  )