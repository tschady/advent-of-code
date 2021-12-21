(ns aoc.2021.d18
  (:require [aoc.file-util :as file-util]
            [clojure.zip :as zip]
            [medley.core :refer [find-first]]))

;; ^:blog
;; My first thought was either `tree-seq` or `clojure.zip`.  With all the
;; navigating necessary, I went with zippers. Although I'm familiar with
;; them from considering their use on previous years, this was my first
;; actual problem with them so I spent some time reading.  Then considerable
;; experimentation to figure out the navigation.
;;
;; [CAUTION]
;; ====
;; I lost hours because I read the instructions wrong.  I was navigating through
;; the tree, finding the first available operation, then doing it.  The instructions
;; say to do all the exploding, then go back and do any splitting.  I had to use
;; printf debugging on the example with mine vs. another person's solution to see
;; where I went wrong.
;; ====
;;
;; Gotta love problem input that's native Clojure code so `read-string` just works.

(def ^:blog input (mapv read-string (file-util/read-lines "2021/d18.txt")))

(defn magnitude
  "Returns the magnitude of this expression.
  The magnitude of a regular number is just that number.
  The magnitude of a pair is 3 times the magnitude of its left
  element plus 2 times the magnitude of its right element."
  [node]
  (if (number? node)
    node
    (+ (* 3 (magnitude (first node)))
       (* 2 (magnitude (second node))))))

;; ^:blog
;; Nice to have the depth along for the ride.

(defn- ^:blog explode? [node]
  (and (coll? (zip/node node))
       (= 4 (count (zip/path node)))))

(defn leaf? [node] (-> node zip/node number?))

(defn- split? [node]
  (let [n (zip/node node)]
    (and (number? n)
         (>= n 10))))

;; ^:blog
;; I use an iterator to lazily navigate the tree in the specified
;; direction: `next` for forward/right, `prev` for backwards/left
;;
;; We need to include this `(not (nil? ...))` check because `zip/end`
;; only works going forwards.  When we go backwards past the root,
;; `nil` is our terminating signal.

(defn ^:blog iter-zip [zipper step-fn]
  (->> zipper
       (iterate step-fn)
       (take-while #(and (not (nil? %))
                         (not (zip/end? %))))))

(defn add-right [zipper v]
  (if-let [right-node (->> (iter-zip zipper zip/next)
                           (drop 1) ; skip current node
                           (find-first leaf?))]
    (zip/edit right-node + v)
    zipper))

(defn add-left [zipper v]
  (if-let [left-node (->> (iter-zip zipper zip/prev)
                          (drop 1) ; skip current node
                          (find-first leaf?))]
    (-> left-node
        (zip/edit + v)
        (add-right 0))  ; kludge to get back to where we were
    zipper))

;; ^:blog Since we need to update two elements, we have to return
;; back to this node only if we updated the left one.  Thus
;; an ugly kludge in the add-left function to return back.

(defn ^:blog explode [zipper]
  (let [[left right] (zip/node zipper)]
    (-> zipper
        (zip/replace 0)
        (add-left left)
        (add-right right)
        zip/root)))

(defn ^:blog split
  "Returns the zipper with this node replaced by a new child node.
  The childs' values are the integer halves of the current value,
  with rounding going to the right value."
  [zipper]
  (let [n     (zip/node zipper)
        left  (quot n 2)
        right (- n left)]
    (-> zipper
        (zip/replace [left right])
        zip/root)))

;; ^:blog
;; The main loop uses iterators to find the next available exploder.
;; If there isn't one, then try and split.  If we don't split, then
;; we're done.
;; TODO: This is currently inefficient, as we re-navigate back to each
;; exploding node, when we could just do all the exploders in turn.
;; (5s for part-2)

(defn ^:blog reduce-snail [data]
  (let [zipper (-> data zip/vector-zip)
        iter   (iter-zip zipper zip/next)]
    (if-let [exploder (find-first explode? iter)]
      (recur (explode exploder))
      (if-let [splitter (find-first split? iter)]
        (recur (split splitter))
        (zip/root zipper)))))

(defn part-1 [input]
  (->> input
       (reduce (fn [a b] (reduce-snail [a b])))
       magnitude))

(defn part-2 [input]
  (reduce max
          (for [a input
                b input
                :when (not= a b)]
            (magnitude (reduce-snail [a b])))))
