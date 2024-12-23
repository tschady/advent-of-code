(ns aoc.2024.d23-test
    (:require
     [aoc.2024.d23 :as sut]
     [clojure.test :refer :all]))

(def ex1 "kh-tc
qp-kh
de-cg
ka-co
yn-aq
qp-ub
cg-tb
vc-aq
tb-ka
wh-tc
yn-cg
kh-ub
ta-co
de-co
tc-td
tb-wq
wh-td
ta-ka
td-qp
aq-cg
wq-ub
ub-vc
de-ta
wq-aq
wq-vc
wh-yn
ka-de
kh-ta
co-tc
wh-qp
tb-vc
td-yn")

(deftest challenges
  (is (= 1337 (sut/part-1 sut/input)))
  (is (= "aw,fk,gv,hi,hp,ip,jy,kc,lk,og,pj,re,sr" (sut/part-2 sut/input))))
