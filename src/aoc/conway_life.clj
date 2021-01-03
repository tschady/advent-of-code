(ns aoc.conway-life
  (:require [clojure.set :as set]
            [medley.core :as medley :refer [filter-vals]]))

(defn next-gen [neighbor-fn live-on-fn repro-fn alives]
  (let [half
        (reduce (fn [acc alive-loc]
                  (let [neighbors          (set (neighbor-fn alive-loc))
                        alive-neighbors   (set/intersection neighbors (set alives))
                        dead-neighbors (set/difference neighbors alive-neighbors)]
                    (-> (if (live-on-fn (count alive-neighbors))
                          (update-in acc [:alive] conj alive-loc)
                          acc)
                        (update-in [:dead] #(merge-with + % (frequencies dead-neighbors))))))
                {}
                alives)]
    (concat (:alive half) (keys (filter-vals repro-fn (:dead half))))))
