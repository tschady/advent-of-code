(ns update-badges
  (:require [babashka.curl :as curl]
            [babashka.pods :as pods]
            [clojure.java.io :as io]
            [clojure.string :as str]))

(pods/load-pod 'retrogradeorbit/bootleg "0.1.9")

(require '[pod.retrogradeorbit.bootleg.utils :refer [convert-to]]
         '[pod.retrogradeorbit.hickory.select :as s])

(def yrs ["2022" "2021" "2020" "2019" "2018" "2017" "2016" "2015"])

(def cookie (str/trim-newline (slurp ".session")))

(def badge-style
  {"color"      "00cc00" ; right side
   "labelColor" "0a0e25" ; left side
   "style"      "flat"
   "logo"       (str "data:image/png;base64," (slurp "img/aoc-favicon-base64"))})

(defn get-stars
  "Return a string representing number of stars earned for a given `year`"
  [year]
  (let [parsed (-> (str "https://adventofcode.com/" year)
                   (curl/get {:debug true
                              :headers {"Cookie" (str "session=" cookie)}})
                   :body
                   (convert-to :hickory))]
    (-> (s/select (s/class "star-count") parsed)
        first
        :content
        first)))

(defn make-badge [year stars]
  (let [params (merge {"label" year, "message" stars} badge-style)]
    (:body (curl/get "http://img.shields.io/static/v1" {:query-params params}))))

(defn save-badge! [year]
  (let [path  (str "img/" year ".svg")
        stars (get-stars year)
        badge (make-badge year stars)]
    (spit path badge)))

(map save-badge! yrs)
