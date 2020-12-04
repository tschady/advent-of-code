(ns aoc.2020.d04
  (:require [aoc.file-util :as file-util]
            [clojure.spec.alpha :as s]
            [clojure.string :as str]))

(def input (file-util/read-chunks "2020/d04.txt"))

(s/def ::byr #(<= 1920 (Integer/parseInt %) 2002))
(s/def ::iyr #(<= 2010 (Integer/parseInt %) 2020))
(s/def ::eyr #(<= 2020 (Integer/parseInt %) 2030))
(s/def ::hcl #(re-matches #"#[0-9a-f]{6}" %))
(s/def ::pid #(re-matches #"\d{9}" %))
(s/def ::ecl #{"amb" "blu" "brn" "gry" "grn" "hzl" "oth"})
(s/def ::hgt (s/or :cm (s/and #(re-matches #"\d+cm" %)
                              #(<= 150 (Integer/parseInt (second (re-find #"(\d+)" %))) 193))
                   :in (s/and #(re-matches #"\d+in" %)
                              #(<= 59 (Integer/parseInt (second (re-find #"(\d+)" %))) 76))))

;; this is weird since the first problem is less specific than the second, but seems cleaner
;;  than turning off some specs
(s/def ::passport-keys #(->> [:eyr :byr :hcl :ecl :hgt :iyr :pid]
                             (map (partial contains? %))
                             (every? true?)))

(s/def ::passport (s/keys :req-un [::eyr ::byr ::hcl ::ecl ::hgt ::iyr ::pid]
                          :opt-un [::cid]))

(defn parse-passport [raw]
  (->> (str/split raw #"[\s\n]")
       (map #(str/split % #":"))
       (into {})
       clojure.walk/keywordize-keys))

(defn solve [input spec] (->> input
                              (map parse-passport)
                              (filter (partial s/valid? spec))
                              count))

(defn part-1 [input] (solve input ::passport-keys))

(defn part-2 [input] (solve input ::passport))
