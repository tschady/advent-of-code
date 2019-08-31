(ns aoc.2016.d07
  (:require [aoc.file-util :as file-util]
            [clojure.string :as str]))

(def input (file-util/read-lines "2016/d07.txt"))

(defn tls?
  "Returns true if IP string contains ABBA pattern outside brackets
  and does not contain ABBA inside brackets (the hypernet),
  else returns false."
  [ip]
  (let [abba "([a-z])(?!\\1)([a-z])\\2\\1"
        hyper-abba? (re-find (re-pattern (str "\\[\\w*" abba "\\w*\\]")) ip)
        abba? (re-find (re-pattern abba) ip)]
    (boolean (and abba? (not hyper-abba?)))))

(defn ssl?
  "Returns true if string contains ABA pattern outside brackets
  and BAB inside brackets, else returns false."
  [ip]
  (let [hyper-re #"\[.*?\]"
        ;; rewrite the string to lump the parts together, with a known
        ;; separator '&', to enable a single pass regex
        grouped-ip (str (str/join (re-seq hyper-re ip))
                        "&"
                        (str/replace ip hyper-re ""))]
    (boolean
     (re-find #"([a-z])(?!\1)([a-z])\1[^&]*&\w*\2\1\2" grouped-ip))))

(defn part-1 [input] (count (filter tls? input)))

(defn part-2 [input] (count (filter ssl? input)))

(comment
  "I wanted to use negative lookahead/behind, but I think impossible,
as there needs to be capture groups to detect ABBA, but capture groups
cannot be inside a non-capturing lookbehind.  I still feel like
there's one regex to solve this instead of munging or multiple re
passes.")
