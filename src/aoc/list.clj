(ns aoc.list
  (:require [clojure.string :as str]))

(defprotocol INode
  "Node operations on a doubly linked list."
  (value [this])
  (get-prev [this])
  (set-prev [this node])
  (get-next [this])
  (set-next [this node]))

(deftype Node [val
               ^:volatile-mutable prv
               ^:volatile-mutable nxt]
  INode
  (value [_] val)
  (get-prev [_] prv)
  (set-prev [this node]
    (set! prv node)
    this)
  (get-next [_] nxt)
  (set-next [this node]
    (set! nxt node)
    this)

  Object
  (toString [_] (str val)))

(defn insert-after
  "Insert `node` into linked list immediately following `left`.  Returns `node`."
  [left node]
  (let [right (get-next left)]
    (set-prev node left)
    (set-next left node)
    (set-prev right node)
    (set-next node right)
    node))

(defn unlink
  "Remove `node` from the linked list, set it's pointers to `nil`, and return it."
  [node]
  (let [left (get-prev node)
        right (get-next node)]
    (set-next left right)
    (set-prev right left)
    (set-prev node nil)
    (set-next node nil)
    node))

(defn shift-right
  "Move `node` from its current place in the list to `n` to the right."
  [node n]
  (if (zero? n)
    node
    (let [target (nth (iterate get-next node) n)]
      (insert-after target (unlink node)))))

(defn make-circular-node
  "Return new node of value `v` with prev and next pointing to itself."
  [v]
  (let [node (Node. v nil nil)]
    (set-prev node node)
    (set-next node node)))

(defn pprint
  "Returns a string representation of list."
  [lst]
  (->> (first lst)
       (iterate get-next)
       (take (count lst))
       (map str)
       (str/join " ")))

(defn make-circ-list
  "Return a collection of Nodes of given values, doubly linked in order.  Last is linked to first."
  [[x & xs]]
  (reduce (fn [nodes v]
            (->> (Node. v nil nil)
                 (insert-after (peek nodes))
                 (conj nodes)))
          (vector (make-circular-node x))
          xs))
