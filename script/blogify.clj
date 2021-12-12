(ns blogify
  "Main entry point for command line use."
  (:gen-class)
  (:require
   [clojure.java.io :as io]
   [clojure.string :as str]
   [marginalia.parser :as parser]))

;; We'll use the `marginalia` parser, but monkeypatched so docstrings still show.
(in-ns 'marginalia.parser)
(defn strip-docstring [_ raw] raw)
(in-ns 'blogify)

(def blog-marker #"\^:blog\s?")

(def base-url "https://github.com/tschady/advent-of-code/blob/main/")

(def partial-dir "doc/partial/")

(defn wrap-source [block]
  (if (= :code (:type block))
    (update block :raw #(str "[source, clojure]\n----\n" % "\n----\n"))
    block))

(defn get-title [path]
  (str/join "." (-> path
                    (str/replace #"\.clj" "")
                    (str/split #"/")
                    rest)))

(defn extract-blog-meta [block]
  (cond-> block
    (re-find blog-marker (:raw block))
    (-> (update :raw #(str/replace % blog-marker ""))
        (assoc :blog true))))

(defn combine-adj-src [parsed section]
  (let [last-section (last parsed)]
    (if (= :code (:type section) (:type last-section))
      (conj (pop parsed) (update last-section :raw #(str % "\n\n" (:raw section))))
      (conj parsed section))))

(defn parse-day [path]
  (->> path
       (parser/parse-file)
       (mapv extract-blog-meta)
       (filterv (partial :blog))
       (reduce combine-adj-src [])
       (map wrap-source)))

(defn render-day [path]
  (let [parsed (parse-day path)
        title  (str "link:../" path "[" (get-title path) "]")
        body   (str/join "\n\n" (map :raw parsed))]
    (when (not-empty body)
      (str "== " title "\n" body))))

(defn render-year [yr]
  (let [dir (str "src/aoc/" yr)
        days (sort (map #(.getPath %) (.listFiles (io/file dir))))
        body (str/join "\n\n" (keep render-day days))
        intro-path (str partial-dir "intro" yr ".adoc")
        intro (when (.exists (io/file intro-path))
                (slurp intro-path))]
    (str intro body)))

(defn output-file [yr]
  (let [output (render-year yr)
        outfile (str "doc/" yr ".adoc")]
    (when (not-empty output)
      (spit outfile output))))

(defn -main [& args]
  ;; Holy hackamole, use tools.cli
  (let [yr (str (first (read-string (first args))))]
    (output-file yr)))
