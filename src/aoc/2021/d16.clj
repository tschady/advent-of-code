(ns aoc.2021.d16
  (:require
   [aoc.file-util :as file-util]
   [aoc.string-util :refer [s->int]]
   [clojure.pprint :refer [cl-format]]
   [medley.core :refer [take-upto]]))

(def input (file-util/read-file "2021/d16.txt"))

;; ^:blog
;; This problem was tedious and painful.  I considered BNF parsing
;; (but the branching is pretty complex), and Java ByteBuffers,
;; before just settling on plain old Clojure.
;; The _only_ thing I like about this code is my use of `cl-format`.

(defn ^:blog hex->bits [hex]
  (cl-format nil "~{~4,'0B~}" (map #(Character/digit % 16) hex)))

(declare parse-packet)

;; ^:blog
;; Since all this data is immutable, all of these `slice-*` fn's
;; take in a stream and return a tuple of the target value and the
;; stream with the target removed. Simulates destructive chomping,
;; but it is absolutely no fun.

(defn ^:blog slice-val [n stream]
  (let [[subj stream] (split-at n stream)]
    [(s->int 2 subj) stream]))

;; ^:blog
;; `medley.core/take-upto` saved some time knowing when to stop
;; chomping the bits.

(defn ^:blog slice-literal [stream]
  (let [val-part (->> stream
                      (partition 5)
                      (take-upto #(= \0 (first %))))
        stream   (drop (count (flatten val-part)) stream)
        value    (->> val-part
                      (map (partial drop 1))
                      flatten
                      (s->int 2))]
    [value stream]))

(defn sub-length [n stream]
  (let [[sub-stream rest-stream] (split-at n stream)]
    (loop [packets []
           stream sub-stream]
      (if (empty? stream)
        [packets rest-stream]
        (let [[packet stream] (parse-packet stream)]
          (recur (conj packets packet) stream))))))

(defn sub-count [n stream]
  (loop [packets []
         stream stream]
    (if (= n (count packets))
      [packets stream]
      (let [[packet stream] (parse-packet stream)]
        (recur (conj packets packet) stream)))))

(defn slice-operator [stream]
  (let [[len stream] (slice-val 1 stream)
        [parser i]  (case len
                      0 [sub-length 15]
                      1 [sub-count 11]
                      (throw (AssertionError. (str "Unknown length code: " len))))
        [n stream] (slice-val i stream)]
    (parser n stream)))

;; ^:blog The main loop is OK.

(defn ^:blog parse-packet [stream]
  (let [[version stream] (slice-val 3 stream)
        [type stream]    (slice-val 3 stream)
        [payload stream] (case type
                           4 (slice-literal stream)
                           (slice-operator stream))]
    [{:version version :type type :payload payload}
     stream]))

(defn sum-versions [{:keys [version type payload]}]
  (case type
    4 version
    (reduce + version (map sum-versions payload))))

;; ^:blog Mapping to functions makes the code readable

(def ^:blog type->op {0 +
                      1 *
                      2 min
                      3 max
                      5 #(if (> %1 %2) 1 0)
                      6 #(if (< %1 %2) 1 0)
                      7 #(if (= %1 %2) 1 0)})

(defn ^:blog evaluate [{:keys [type payload]}]
  (if (= 4 type)
    payload
    (reduce (type->op type) (map evaluate payload))))

(defn part-1 [input]
  (sum-versions (first (parse-packet (hex->bits input)))))

(defn part-2 [input]
  (evaluate (first (parse-packet (hex->bits input)))))
