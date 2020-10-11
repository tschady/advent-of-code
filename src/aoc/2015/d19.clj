(ns aoc.2015.d19
  (:require [aoc.file-util :as file-util]
            [aoc.string-util :as string-util]))

(def input (file-util/read-lines "2015/d19.txt"))

(defn parse-rules [lines] (->> lines
                               (map #(re-find #"^(\w+) => (\w+)$" %))
                               (keep identity)
                               (map rest)))

(def rules (parse-rules input))

(def molecule (last input))

(defn part-1 [molecule rules]
  (let [combos (for [[pattern replacement] rules
                     idx (string-util/match-indices pattern molecule)]
                 (string-util/substring-replace molecule idx replacement))]
    (count (distinct combos))))

(defn steps-to-build
  "Return number of build steps required to transform `start` into `goal`
  using substitution `rules`."
  [goal start rules]
  ;; algorithm is to begin from the finish state, and replace groups
  ;; with the largest regex possible until `start` state is achieved.
  (let [sorted-rules (sort-by #(count (second %)) > rules)]
    (loop [num-steps 0
           molecule  goal]
      (if (= molecule start)
        num-steps
        (if-let [new-mol (reduce
                          (fn [_ [replacement target]]
                            (let [idxs (string-util/match-indices target molecule)]
                              (when (seq idxs)
                                (reduced (string-util/substring-replace molecule (first idxs) replacement)))))
                          nil
                          sorted-rules)]
          (recur (inc num-steps) new-mol)
          (str "Deadend:" molecule))))))

(defn part-2 [medicine rules]
  (steps-to-build medicine "e" rules))
