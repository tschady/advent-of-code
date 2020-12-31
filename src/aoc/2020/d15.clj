(ns aoc.2020.d15)

(def input [0 14 6 20 1 4])

(defn van-eck [init target]
  (loop [last-said (peek init)
         history (zipmap (butlast init) (map inc (range)))
         idx (count init)]
    (if (== idx target)
      last-said
      (let [last-time (get history last-said idx)]
        (recur (- idx last-time)
               (assoc history last-said idx)
               (inc idx))))))

(defn part-1 [input] (van-eck input 2020))

(defn part-2 [input] (van-eck input 30000000))
