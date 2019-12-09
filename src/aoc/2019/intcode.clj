(ns aoc.2019.intcode)

(defn make-prog
  ([tape] (make-prog tape 0))
  ([tape input] {:ptr 0 :mem tape :in input :out []}))

(def opcodes {1 {:fn +, :size 4}
              2 {:fn *, :size 4}
              3 {:fn identity, :size 2}
              4 {:fn identity, :size 2}
              99 {:fn :halt, :size 1}})

(defn set-noun-verb [prog noun verb] (assoc prog 1 noun 2 verb))

(defn run-prog [{:keys [ptr mem in out] :as prog}]
  (let [inst (get mem ptr)
        opcode (rem inst 100)
        mode1 (-> inst (quot 100) (mod 10))
        mode2 (-> inst (quot 1000))
        v1 (get mem (+ 1 ptr))
        v2 (get mem (+ 2 ptr))
        v3 (get mem (+ 3 ptr))
        op-param (get opcodes opcode)
        new-ptr (+ ptr (:size op-param))
        arg1 (if (pos? mode1) v1 (get mem v1))
        arg2 (if (pos? mode2) v2 (get mem v2))]
    (if (= 99 opcode)
      prog
      (let [prog-next (case opcode
                        1 (assoc-in prog [:mem v3] (+ arg1 arg2))
                        2 (assoc-in prog [:mem v3] (* arg1 arg2)))]
        (recur (assoc prog-next :ptr new-ptr))))))
