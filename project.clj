(defproject aoc "0.1.0-SNAPSHOT"
  :description "Clojure solutions for multiple years of Advent of Code"
  :url "http://adventofcode.com"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.9.0"]
                 [org.flatland/ordered "1.5.6"]
                 [pandect "0.6.1"]
                 [aysylu/loom "1.0.2"]
                 [org.clojure/math.combinatorics "0.1.4"]]
  :main ^:skip-aot aoc.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
