(ns aoc.2020.d23
  (:require [aoc.string-util :as string-util]))

(def input (vec (string-util/explode-digits "186524973")))

(defrecord Cup [label next])

(defn make-cup [label] (->Cup label (ref nil)))

(defn find-dest-label
  [n max-n snippet-vals]
  (let [target (dec n)]
    (cond
      (contains? (set snippet-vals) target) (recur target max-n snippet-vals)
      (>= 0 target) (recur (inc max-n) max-n snippet-vals)
      :else target)))

(defn get-next-labels
  "Get the values of the next `n` nodes to the right of `cup`"
  [cup n]
  (->> cup
       (iterate #(deref (:next %)))
       (drop 1)
       (take n)
       (map :label)))

(defn init-state
  "Construct the initial game state from ordered vector of input `labels`.
  State is map of value (label) to node (cup), where each node is a value and mutable ref
  to the next node in the chain.  State includes the current node (:cursor), and a cached
  value of the maximum label.

  The linked list lets us quickly change the order of the cups, and the hashmap lets us
  jump to a node with a given value in constant time."
  [labels max-label]
  (let [ring (reduce (fn [state label]
                       (let [new-cup (->Cup label (ref (get state :head)))]
                         (-> state
                             (update :label->cup conj [label new-cup])
                             (assoc :head new-cup))))
                     {:label->cup {}
                      :head     nil}
                     (reverse labels))
        tail (get-in ring [:label->cup (last labels)])
        head (get ring :head)]
    (dosync (ref-set (:next tail) head))
    (-> ring
        (assoc :cursor (:head ring))
        (assoc :max max-label))))

(defn advance
  "Return the node on the list `n` links forward."
  [curr n]
  (->> curr
    (iterate #(deref (:next %)))
    (take (inc n))
    last))

(defn move
  "Perform one turn of the game, returning updated `state`."
  [state]
  (let [curr (:cursor state)
        snippet-vals (get-next-labels curr 3)
        snippet-head (get-in state [:label->cup (first snippet-vals)])
        snippet-tail (get-in state [:label->cup (last snippet-vals)])
        dest-label (find-dest-label (:label curr) (:max state) snippet-vals)
        dest-cup (get-in state [:label->cup dest-label])]
    (dosync
     (ref-set (:next curr) (advance curr 4))
     (ref-set (:next snippet-tail) (advance dest-cup 1))
     (ref-set (:next dest-cup) snippet-head))
    (assoc state :cursor (deref (:next curr)))))

(defn part-1 [input]
  (apply str (-> (iterate move (init-state input 9))
                 (nth 100)
                 (get-in [:label->cup 1])
                 (get-next-labels 8))))

(defn part-2 [input]
  (apply * (-> (iterate move (init-state (into input (range 10 1000001)) 1000000))
               (nth 10000000)
               (get-in [:label->cup 1])
               (get-next-labels 2))))
