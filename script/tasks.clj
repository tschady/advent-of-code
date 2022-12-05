(ns tasks
  (:require
   [babashka.curl :as curl]
   [babashka.pods :as pods]
   [babashka.process :as p]
   [clojure.java.shell :refer [sh]]
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

(defn- problem-url [y d] (str aoc-url "/" y "/day/" d))
(defn- input-url   [y d] (str (problem-url y d) "/input"))
(defn- source-path [y d] (format "src/aoc/%s/d%s.clj"       y (zero-pad-str d)))
(defn- test-path   [y d] (format "test/aoc/%s/d%s_test.clj" y (zero-pad-str d)))
(defn- input-path  [y d] (format "resources/%s/d%s.txt"     y (zero-pad-str d)))

(defn template-day
  "Create stub clj and test file for given day, from template."
  [{:keys [y d] :or {y current-year d current-day}}]
  (let [d0 (zero-pad-str d)]
    (spit (source-path y d) (render-file "templates/src.clj" {:year y :day d0}))
    (spit (test-path y d) (render-file "templates/test.clj" {:year y :day d0}))))

(defn download-input
  "Download the problem input for given day, and save to correct path."
  [{:keys [y d] :or {y current-year d current-day}}]
  (spit (input-path y d) (:body (curl/get (input-url y d) headers))))

(defn- save-badge
  "Create badge with year label and star count, and save to file."
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

(defn open-apps
  "Fire up all applications required to solve the problem."
  [{:keys [y d] :or {y current-year d current-day}}]
  (p/process (str "emacsclient -c " (source-path y d)))
  (sh "open" (input-url y d))
  (sh "open" (problem-url y d)))
