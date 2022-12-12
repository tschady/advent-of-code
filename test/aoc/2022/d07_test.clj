(ns aoc.2022.d07-test
  (:require
   [aoc.2022.d07 :as sut]
   [clojure.test :refer :all]))

(def example
"$ cd /
$ ls
dir a
14848514 b.txt
8504156 c.dat
dir d
$ cd a
$ ls
dir e
29116 f
2557 g
62596 h.lst
$ cd e
$ ls
584 i
$ cd ..
$ cd ..
$ cd d
$ ls
4060174 j
8033020 d.log
5626152 d.ext
7214296 k")

(deftest challenges
  (is (= 1582412 (sut/part-1 sut/input)))
  (is (= 3696336 (sut/part-2 sut/input))))
