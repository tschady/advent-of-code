(ns aoc.2019.intcode)

(defn make-prog
  ([tape] (make-prog tape [0]))
  ([tape input] {:ptr 0 :mem tape :in input :out []}))

(defn set-noun-verb [prog noun verb] (assoc prog 1 noun 2 verb))

(defn run-prog [{:keys [ptr mem in out] :as prog}]
  (let [inst (get mem ptr)
        opcode (rem inst 100)
        mode1 (-> inst (quot 100) (mod 10))
        mode2 (-> inst (quot 1000))
        v1 (get mem (+ 1 ptr))
        v2 (get mem (+ 2 ptr))
        v3 (get mem (+ 3 ptr))
        arg1 (if (pos? mode1) v1 (get mem v1))
        arg2 (if (pos? mode2) v2 (get mem v2))]
    (if (= 99 opcode)
      prog
      (let [prog-next (case opcode
                        1 (-> prog
                              (assoc-in [:mem v3] (+ arg1 arg2))
                              (update :ptr #(+ % 4)))
                        2 (-> prog
                              (assoc-in [:mem v3] (* arg1 arg2))
                              (update :ptr #(+ % 4)))
                        3 (-> prog
                              (assoc-in [:mem v1] (first in))
                              (update :ptr #(+ % 2))
                              (assoc :in (rest in)))
                        4 (-> prog
                              (update :out #(conj % (get mem v1)))
                              (update :ptr #(+ % 2)))
                        5 (-> prog
                              (assoc :ptr (if (zero? arg1)
                                            (+ ptr 3)
                                            arg2)))
                        6 (-> prog
                              (assoc :ptr (if (zero? arg1)
                                            arg2
                                            (+ ptr 3))))
                        7 (-> prog
                              (assoc-in [:mem v3] (if (< arg1 arg2) 1 0))
                              (update :ptr #(+ % 4)))

                        8 (-> prog
                              (assoc-in [:mem v3] (if (= arg1 arg2) 1 0))
                              (update :ptr #(+ % 4)))

)]
        (recur prog-next)))))


#_(def p [3,21,1008,21,8,20,1005,20,22,107,8,21,20,1006,20,31,
              1106,0,36,98,0,0,1002,21,125,20,4,20,1105,1,46,104,
              999,1105,1,46,1101,1000,1,20,4,20,1105,1,46,98,99])

#_(run-prog (make-prog p 5))


