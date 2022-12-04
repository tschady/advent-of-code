(ns tasks
  (:require
   [babashka.curl :as curl]
   [babashka.pods :as pods]
   [selmer.parser :refer [render-file]]))

(pods/load-pod 'retrogradeorbit/bootleg "0.1.9")

(require '[pod.retrogradeorbit.bootleg.utils :refer [convert-to]]
         '[pod.retrogradeorbit.hickory.select :as s])

(def current-year (str (.getYear (java.time.LocalDate/now))))
(def current-day (str (.getDayOfMonth (java.time.LocalDate/now))))

(def aoc-url "https://adventofcode.com")
(def badge-url "http://img.shields.io/static/v1")
(def icon-path "img/aoc-favicon-base64")

(def headers {:headers
              {"Cookie"    (str "session=" (System/getenv "AOC_SESSION"))
               "UserAgent" (str (System/getenv "AOC_REPO")
                                " by "
                                (System/getenv "AOC_EMAIL"))}})

(def badge-style
  {"color"      "00cc00" ; right side
   "labelColor" "0a0e25" ; left side
   "style"      "flat"
   "logo"       (str "data:image/png;base64," (slurp icon-path))})

(defn- zero-pad-str [s] (format "%02d" (Long/valueOf s)))

(defn template-day
  ""
  [{:keys [y d] :or {y current-year d current-day}}]
  (let [d       (zero-pad-str d)
        outsrc  (format "src/aoc/%s/d%s.clj" y d)
        outtest (format "test/aoc/%s/d%s_test.clj" y d)]
    (spit outsrc (render-file "templates/src.clj" {:year y :day d}))
    (spit outtest (render-file "templates/test.clj" {:year y :day d}))))

(defn download-input
  ""
  [{:keys [y d] :or {y current-year d current-day}}]
  (let [url (str aoc-url "/" y "/day/" d "/input")
        dest (format "resources/%s/d%s.txt" y (zero-pad-str d))]
    (spit dest (:body (curl/get url headers)))))

(defn- save-badge
  ""
  [[label stars]]
  (let [path  (str "img/" label ".svg")
        params (merge {"label" label, "message" stars} badge-style)
        badge (:body (curl/get badge-url {:query-params params}))]
    (spit path badge)))

(defn update-badges [arg]
  (let [parsed     (-> (str aoc-url "/events")
                       (curl/get headers)
                       :body
                       (convert-to :hickory))
        stars      (->> parsed
                        (s/select (s/class "star-count"))
                        (drop 1) ; ignore the stars listed by login
                        (mapcat :content))
        all-yrs    (mapv str (reverse (range 2015 (inc (Long/valueOf current-year)))))
        yrs->stars (zipmap (conj all-yrs "Total") stars)]
    (run! save-badge yrs->stars)))
