(ns aoc.2020.d19
  (:require [aoc.file-util :as file-util]
            [clojure.string :as str]
            [instaparse.core :as insta]))

(def input (file-util/read-chunks "2020/d19.txt"))

(defn solve [[rules msg-str]]
  (let [parser (insta/parser rules :start :0)]
    (->> msg-str
         str/split-lines
         (remove #(insta/failure? (parser %)))
         count)))

(defn part-1 [input] (solve input))

(defn part-2 [input]
  (let [[rules messages] input
        mod-rules        (-> rules
                             (str/replace "8: 42" "8: 42 | 42 8")
                             (str/replace "11: 42 31" "11: 42 31 | 42 11 31"))]
    (solve [mod-rules messages])))
