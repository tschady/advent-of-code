(ns aoc.2020.d13
  (:require
   [aoc.file-util :as file-util]
   [aoc.string-util :as string-util]
   [clojure.string :as str]))

#_(set! *unchecked-math* :warn-on-boxed)
#_(set! *warn-on-reflection* true)

(def input (file-util/read-lines "2020/d13.txt"))

(defn part-1 [input]
  (let [[[earliest-depart] bus-list] (map string-util/ints input)]
    (->> bus-list
         (map #(vector % (- % (rem earliest-depart %))))
         (apply min-key second)
         (apply *))))

(def x ["_" "7,13,x,x,59,x,31,19"])

(defn parse2 [input]
  (->> (str/split (second input) #",")
       (keep-indexed (fn [idx elem]
                       (when (re-matches #"\d+" elem)
                         [idx (Integer/parseInt elem)])))))

(def i2 (parse2 x))

(defn part-2 [input]
  (let [parsed      (parse2 input)
        divs        (map second parsed)
        rems        (map (comp - first) parsed)
        product     (reduce * divs)
        subproducts (map #(/ product %) divs)
        inv-mods    (map (fn [sp d]
                           (int (.modInverse (biginteger sp) (biginteger d))))
                         subproducts
                         divs)]
    (mod (apply + (map * inv-mods subproducts rems))
         product)))

(part-2 input);1010182346291467

;; (rem t 7) = 0                (rem t 7) = 0
;; (- 13 (rem t 13)) = 1,       (rem t 13) = 12
;; (- 59 (rem t 59)) = 4,       (rem t 59) = 55
;; (- 31 (rem t 31)) = 6        (rem t 31) = 25
;; (- 19 ((rem t 19)) = 7       (rem t 19) = 12
