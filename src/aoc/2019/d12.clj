(ns aoc.2019.d12
  (:require [aoc.file-util :as file-util]
            [aoc.math-util :as math-util]
            [clojure.math.numeric-tower :as math]))

(def input (file-util/read-lines "2019/d12.txt"))

(defn parse-pos [s] (map read-string (re-seq #"-?\d+" s)))

(defn build-moon [[x y z]] {:x  x :y  y :z  z
                            :dx 0 :dy 0 :dz 0})

(defn pot-energy [moon] (+ (Math/abs (:x moon))
                           (Math/abs (:y moon))
                           (Math/abs (:z moon))))

(defn kin-energy [moon] (+ (Math/abs (:dx moon))
                           (Math/abs (:dy moon))
                           (Math/abs (:dz moon))))

(defn energy [moon] (* (pot-energy moon) (kin-energy moon)))

(defn apply-gravity [target-moon source-moon]
  (-> target-moon
      (update :dx #(+ % (compare (:x source-moon) (:x target-moon))))
      (update :dy #(+ % (compare (:y source-moon) (:y target-moon))))
      (update :dz #(+ % (compare (:z source-moon) (:z target-moon))))))

(defn apply-velocity [moon]
  (-> moon
      (update :x #(+ % (:dx moon)))
      (update :y #(+ % (:dy moon)))
      (update :z #(+ % (:dz moon)))))

(defn tick-system [moons]
  (->> moons
       (map #(reduce apply-gravity % moons))
       (map #(apply-velocity %))))

(defn period
  "Determine the periodicity of the system along the `dim` axis.  This is calculated
  by the midpoint being when the velocities of each moon along that axis are zero
  (they turn around)."
  [sys dim]
  (->> (iterate tick-system sys)
       (drop 1)
       (take-while #(not-every? zero? (map dim %)))
       count
       inc
       (* 2)))

(defn part-1 [input t]
  (let [sys (->> input (map parse-pos) (map build-moon))
        future-sys (nth (iterate tick-system sys) t)]
    (apply + (map energy future-sys))))

(defn part-2 [input]
  (let [sys (->> input (map parse-pos) (map build-moon))]
    (reduce math/lcm (map (partial period sys) [:dx :dy :dz]))))
