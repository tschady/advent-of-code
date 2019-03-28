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
  [[x y] {:dir c :turns (cycle [:left :straight :right])}])

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
                              :carts (apply hash-map cart)
                              :crashes []}))))

(defn- update-cart
  ""
  [[pos {:keys [dir turns] :as detail} :as cart] track]
  (let [delta (get-in cart-map [dir :move])
        new-pos (mapv + pos delta)
        new-cart (make-cart new-pos detail)
        new-segment (track new-pos)
        next-turn (first turns)]
    (cond
      (corner? new-segment)
      (update-in new-cart [1 :dir] #((:corner->turn (cart-map %)) new-segment))

      (intersection? new-segment)
      (-> new-cart
          (update-in [1 :dir] turn-cart next-turn)
          (update-in [1 :turns] rest))

      :else new-cart)))

(defn update-world
  "Tick time once in world.  Carts move, turn, and sometimes crash.
  Return new world object one time-step later."
  [world]
  (reduce (fn [acc [old-pos _ :as cart]]
            (let [new-cart (update-cart cart (:track acc))
                  new-pos (first new-cart)
                  objects (set (conj (:crashes acc) (map first (:carts acc))))
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
          (into (sorted-map) (:carts world))))


(defn part-1
  ""
  [input]
  (let [world (make-world input)]
    (->> (iterate update-world world)
         (drop-while #(empty? (:crashes %)))
         :crashes
         first
         key)))

(defn part-2
  ""
  [input]
  (let [world (make-world input)]
    (->> (iterate update-world world)
         (drop-while #(> 1 (count (:carts %))))
         :carts
         first
         key)))

;;;;;;;;;;;;;;;;;;;;;;;;;

(def ti ["/->-\\"
         "|   |  /----\\"
         "| /-+--+-\\  |"
         "| | |  | v  |"
         "\\-+-/  \\-+--/"
         "\\------/"])

(def w (make-world ti))

(def c (first (:carts w)))
(def t (:track w))


(defn test-update-c [[pos {:keys [dir turns]} :as cart]]
  {:pos pos
   :dir dir
   :turns turns
   :cart cart}
  )




(def c2 (update-cart c t))

c

(-> c
    (update-cart t))


c2

(first c2)



(update-in c [1 :turns] rest)


; (update-world w)


