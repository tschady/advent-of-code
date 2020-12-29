(ns aoc.2020.d14
  (:require [aoc.file-util :as file-util]))

(def input (file-util/read-lines "2020/d14.txt"))

(def mask "XXXXXXXXXXXXXXXXXXXXXXXXXXXXX1XXXX0X")

(defn decode-v1 [mask value]
  (reduce-kv (fn [val idx digit]
               (condp = digit
                 \1 (bit-set val idx)
                 \0 (bit-clear val idx)
                 \X val))
             value
             (vec (reverse mask))))

(defn decode-v2 [mask value]
  (reduce-kv (fn [values idx digit]
               (mapcat #(condp = digit
                          \1 [(bit-set % idx)]
                          \0 [%]
                          \X [(bit-set % idx) (bit-clear % idx)])
                       values))
             [value]
             (vec (reverse mask))))

;; TODO - refactor commonalities?  
(defn run-prog-v1 [input]
  (reduce (fn [acc s]
            (condp re-matches s
              #"mask = ([01X]+)$"      :>> #(assoc acc :mask (nth % 1))
              #"mem\[(\d+)\] = (\d+)$" :>> #(let [reg    (nth % 1)
                                                  val    (Long/parseLong (nth % 2))
                                                  masked (decode-v1 (get acc :mask) val)]
                                              (assoc-in acc [:mem reg] masked))))
          {:mask "X" :mem {}}
          input))

(defn run-prog-v2 [input]
  (reduce (fn [acc s]
            (condp re-matches s
              #"mask = ([01X]+)$"      :>> #(assoc acc :mask (nth % 1))
              #"mem\[(\d+)\] = (\d+)$" :>> #(let [reg  (Long/parseLong (nth % 1))
                                                  regs (decode-v2 (get acc :mask) reg)
                                                  val  (Long/parseLong (nth % 2))]
                                              (reduce (fn [acc r] (assoc-in acc [:mem r] val))
                                                      acc
                                                      regs))))
          {:mask "X" :mem {}}
          input))

(defn part-1 [input] (reduce +' 0N (vals (:mem (run-prog-v1 input)))))

(defn part-2 [input] (reduce +' 0N (vals (:mem (run-prog-v2 input)))))
