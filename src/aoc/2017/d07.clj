(ns aoc.2017.d07
  (:require [aoc.file-util :as file-util]
            [instaparse.core :as insta]
            [ubergraph.alg :as alg]
            [ubergraph.core :as uber]))
;;; Intentional overkill with heavyweight parsing and graph libraries

(def input (file-util/read-file "2017/d07.txt"))

(def grammar
  "graph = (node (<NL>)?)+
   node = name <' ('> wt <')'> children?
   children = <' -> '> (child)+
   <child> = name (<', '>)?
   <name> = #'[a-z]+'
   wt = #'[0-9]+'
   NL = '\n'")

(defn make-node
  ([name wt]
   (uber/digraph [name {:wt wt}]))
  ([name wt children]
   (apply uber/digraph (cons [name {:wt wt}] (map #(vector name %) children)))))

(defn make-graph [input]
  (->> ((insta/parser grammar) input)
       (insta/transform {:children vector
                         :wt       read-string
                         :node     make-node
                         :graph    uber/digraph})))

(defn fix-wt-broken-child
  "Returns the node weight required to balance the unbalanced node, given a graph and
  weight hashmap of the siblings of the unbalanced node."
  [g wt-map]
  (let [[[bad-wt [[n _]]] [good-wt _]] (sort-by #(count (second %)) (group-by second wt-map))
        dwt (- good-wt bad-wt)
        curr-wt (uber/attr g n :wt)]
    (+ dwt curr-wt)))

(defn part-1 [input]
  (-> input make-graph alg/topsort first))

(defn part-2 [input]
  (let [g (-> input make-graph)]
    (reduce (fn [g n]
              (let [children (uber/successors g n)
                    curr-wt  (uber/attr g n :wt)]
                (if children
                  (let [wt-map (zipmap children (map #(uber/attr g % :tower-wt) children))
                        wts    (vals wt-map)]
                    (if (apply = wts)
                      ;; balanced here, set new subtower weight in current node
                      (uber/add-attr g n :tower-wt (apply + (conj wts curr-wt)))
                      ;; unbalanced, one of our children is broken
                      (reduced (fix-wt-broken-child g wt-map))))
                  ;; no children, this is leaf node, set tower wt to the leaf wt
                  (uber/add-attr g n :tower-wt curr-wt))))
            g
            (alg/post-traverse g))))
