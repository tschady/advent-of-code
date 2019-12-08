(ns aoc.2019.intcode)

(def opcodes {1 +, 2 *, 99 :terminate})

(defn- run-step [tape [opcode p1 p2 result-pos]]
  (let [v1 (tape p1)
        v2 (tape p2)
        result ((get opcodes opcode) v1 v2)]
    (assoc tape result-pos result)))

(defn set-noun-verb [prog noun verb] (assoc prog 1 noun 2 verb))

(defn run-prog
  ([prog]
   (loop [memory prog
          pointer  0]
     (if (= :terminate (get opcodes (memory pointer)))
       memory
       (recur (run-step memory (subvec memory pointer (+ 4 pointer)))
              (+ 4 pointer))))))
