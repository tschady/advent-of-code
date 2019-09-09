(ns aoc.2018.d10
  (:require [aoc.file-util :as file-util]))

(def input (file-util/read-lines "2018/d10.txt"))

(count input)

(defn make-pixel
  "Create pixel object from input string, with cartesian pos and velocity."
  [s]
  (let [[x y dx dy] (mapv read-string (re-seq #"-?\d+" s))]
    {:x x :y y :dx dx :dy dy}))

(defn pixel-at-time
  ""
  [t {:keys [x y dx dy] :as pixel}]
  (-> pixel
      (update-in [:x] #(+ % (* t dx)))
      (update-in [:y] #(+ % (* t dy)))))

(defn tick-pixel
  ""
  [{:keys [x y dx dy] :as pixel}]
  (-> pixel
      (update-in [:x] #(+ % dx))
      (update-in [:y] #(+ % dy))))

(defn bounding-box
  "Returns the [:dx :dy :min-x :min-y] dimensions of the pixel collection."
  [pixels]
  (let [min-x (apply min (map :x pixels))
        min-y (apply min (map :y pixels))
        max-x (apply max (map :x pixels))
        max-y (apply max (map :y pixels))]
    {:dx (- max-x min-x)
     :dy (- max-y min-y)
     :min-x min-x
     :min-y min-y}))

(defn time-to-smallfoo
  ""
  [pixels]
  (let [box0 (bounding-box pixels)
        box1 (bounding-box (map tick-pixel pixels))
        ddydt (- (:dy box0) (:dy box1))
        init-dy (:dy box0)]
    (quot init-dy ddydt)))

;;;;;
(def s1 "position=<2, 3> velocity=<1, 1>")
(def s2 "position=<-3, -2> velocity=<-1, 1>")
(def c [(first input) (second input)])
(def p1 (make-pixel s1))
(def p2 (make-pixel s2))

(def ps (map make-pixel c))

ps
(map (partial pixel-at-time 3) ps)

(bounding-box ps)

(bounding-box (map (partial pixel-at-time 10894) ps)) ;; 10894

(make-pixel s1)


(apply - (map :dy (take 2 (map bounding-box (iterate #(map (partial tick-pixel) %) (map make-pixel input))))))


p1
ps

(def pixels (map make-pixel input))

(time-to-smallfoo pixels)
