(ns aoc.2021.d24
  (:require
   [aoc.file-util :as file-util]
   [aoc.string-util :as string-util]))

(def input (file-util/read-lines "2021/d24.txt"))

;; ^:blog
;;
;; CAUTION: I enjoyed the aha! moment of this puzzle.  Major spoilers
;; ahead.  It's worth figuring on your own.
;;
;; Sometimes I'll kick off a brute force search while figuring out the
;; optimal method.  But with a search space of stem:[10^14], that was
;; never going to work.  So into the data we go.
;;
;; === Observations
;;
;; I could see the 14 sections (one for each input) differed only on 3
;; lines:
;;
;; - 4: `div z {1,26}` (which I label `d`)
;; - 5: `add x <int>` (labelled `a`)
;; - 15: `add y <int>`, (labelled `b`)
;;
;; `d` covaries with `a`, so we don't need to track it.  (`d` is
;; always 1 if `a` is a positive integer, else 26). We'll call each
;; input stem:[m_i], for "m"odel.
;;
;; .*Equations embedded in the algorithm*
;; * Match: stem:[m_i = z_(i-1) mod 26 + a_i]
;; * Embiggening: stem:[z_i = 26 * z_(i-1) + m_i + b_i]
;; * Shrinkulation: stem:[z_i = floor(z_(i-1) -: 26)]
;;
;; Also:
;;
;; - `w` is never modified, it only gets the current input
;; - `x` and `y` are zeroed out at the start of each run
;; - `z` is the acculumator
;; - `x` is either 0 if there is a Match, else 1.
;; - `a` is never from 1-9, so no positive `a` can result in a Match
;; - stem:[AA x, (26 * x) mod 26 = 0]
;;
;; Therefore there are two things that can happen:
;;
;; 1. no Match (a > 9): `z` gets Embiggened
;; 1. Match (a < 1): `z` gets Shrinkulated
;;
;; For `z` to be zero after 14 runs, there needs to be an equal number
;; of embiggenings and shrinkulations.  For my input, this means all
;; possible matches are matches.
;;
;; On to the code.
;;
;; ---

;; ^:blog We only care about `a` and `b`, so cherry-pick them from the
;; input.

(defn ^:blog parse [input]
  (->> input
       (partition 18)
       (map (juxt #(nth % 5) #(nth % 15)))
       (map (partial apply str))
       (map string-util/ints)))

;; ^:blog
;; === Key idea
;;
;; By stepping through the data, I could see that embiggening rolls up
;; the previous `z` inside a multiplication with 26, which hides it
;; once mod 26 occurs, and adds in new information: stem:[m_i + b_i].
;; Shrinkulation destroys that last bit of new info by dividing by 26
;; and truncating, and simultaneously exposes the last information
;; added.  OMG it's a stack, what a great puzzle.

(defn ^:blog det-digit-rels
  "Returns a collection of `[i1, i2, n]` tuples, where `n` is the
  difference between the two indices `i1`, `i2` of the model number"
  [data]
  (loop [data data
         i 0
         stack []
         rels []]
    (if-let [[a b] (first data)]
      (cond
        (< 9 a) (recur (next data) (inc i) (conj stack [i b]) rels)
        (> 1 a) (let [[i0 b0] (peek stack)]
                  (recur (next data) (inc i) (pop stack) (conj rels [i i0 (+ a b0)]))))
      rels)))

;; ^:blog With the known differences between number pairs, this
;; function finds the integer pair that satisfies the max = 9, min = 1
;; target of parts 1 and 2.

(defn ^:blog rel->nums
  "Returns a tuple of two digits, [a b], where b = a + diff,
  one of a or b is the target, and both numbers are from 1->9.
  e.g. (rel->nums 9 4)  => [5 9]
       (rel->nums 9 -2) => [9 7]
       (rel->nums 1 5)  => [1 6]"
  [target diff]
  (if (< 0 (+ target diff) 10)
    [(+ target diff) target]
    [target (- target diff)]))

(defn ^:blog build-model
  "For a given `target` number (9 if we're trying for highest, 1 for lowest),
  and pairwise digit relations `rels`, a collection of [d0 d1 diff]
  tuples, return the model number that fits all digit relations,
  trending towards the target."
  [target rels]
  (reduce (fn [n [i0 i1 diff]]
            (let [[v0 v1] (rel->nums target diff)]
              (assoc n i0 v0 i1 v1)))
   (vec (repeat 14 0))
   rels))

(defn ^:blog solve [input target]
  (->> (parse input)
       det-digit-rels
       (build-model target)
       (apply str)))

(defn ^:blog part-1 [input] (solve input 9))

(defn ^:blog part-2 [input] (solve input 1))
