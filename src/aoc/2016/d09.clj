(ns aoc.2016.d09
  (:require [aoc.file-util :as file-util]))

(def input (file-util/read-file "2016/d09.txt"))

(defn uncompress [s]
  (loop [input s
         output ""]
    (if (empty? input)
      output
      (if-let [[_ ns xs s] (re-find #"^\((\d+)x(\d+)\)(.*?)$" input)]
        (let [n (read-string ns)
              x (read-string xs)]
          (recur (subs s n) (apply str output (repeat x (subs s 0 n)))))
        (recur (subs input 1) (str output (subs input 0 1)))))))


(defn uncompress-len [s]
  (loop [input s
         len 0]
    (if (empty? input)
      len
      (if-let [[_ ns xs s] (re-find #"^\((\d+)x(\d+)\)(.*?)$" input)]
        (let [n (read-string ns)
              x (read-string xs)]
          (recur (subs s n) (+ len (* n x))))
        (recur (subs input 1) (inc len))))))

(defn uncompress-recurse [s]
  (loop [input s
         len 0]
    (if (empty? input)
      len
      (if-let [[_ ns xs s] (re-find #"^\((\d+)x(\d+)\)(.*?)$" input)]
        (let [n (read-string ns)
              x (read-string xs)]
          (recur (subs s n) (+ len (uncompress-recurse (apply str (repeat x (subs s 0 n)))))))
        (recur (subs input 1) (inc len))))))

(defn part-1 [input] (uncompress-len input))

(defn part-2 [input] (uncompress-recurse input))

(part-2 input); 11658395076

;;;;;;;;;

(def s1 "(6x1)(1x3)A")
(def s2 "ADVENT")
(def s3 "A(1x5)BC")

(empty? "")

(subs "ADVENT" 3)

(repeat 5 "ABC")

(apply str "a" (repeat 5 (subs s2 0 3)))

(subs s2 3)

(rest "ABC")

()
