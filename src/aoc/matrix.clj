(ns aoc.matrix)

(defn transpose
  "Return the matrix transpose (switching cols for rows) of given 2D matrix"
  [m]
  (apply mapv vector m))

(defn rot-r
  "Return the matrix resulting from a 90 degree rotation to the right."
  [m]
  (map reverse (transpose m)))

(defn flip-x
  "Reverse the rows of a matrix."
  [m]
  (map reverse m))

(defn flip-y
  "Reverse the columns of a matrix."
  [m]
  (-> m transpose flip-x transpose))

(defn corner-idxs
  "Returns the 4 indexes of the corners of a 2D square of `size` dimensions."
  [size]
  (for [y [0 (dec size)]
        x [0 (dec size)]]
    (+ x (* size y))))

(defn rotate-seq-left
  "Return the sequence `xs` rotated to the left by `n` elements"
  [n xs]
  (let [c (mod n (count xs))]
    (into [] (concat (drop c xs) (take c xs)))))

(defn rotate-seq-right
  "Return the sequence `xs` rotate to the right by `n` elements."
  [n xs]
  (rotate-seq-left (* -1 n) xs))

(defn initialize
  "Create a 2D matrix of `x` by `y` dimensions, initialied to `v`.
  Matrix is a vector of vectors."
  [x y v]
  (vec (repeat y (vec (repeat x v)))))

(defn pprint
  "Print matrix suitable for buffer eval"
  [m]
  (map (partial apply str) m))

(defn rot-row-right
  "Update row `r` of matrix `m` by rotating `n` places to the right (pos x), wrapping around."
  [m r n]
  (update-in m [r] #(rotate-seq-right n %)))

(defn rot-col-down
  "Update col `c` of matrix `m` by rotating `n` place down (pos y), wrapping around."
  [m c n]
  (-> m transpose (rot-row-right c n) transpose))

(defn set-element
  "Update the value to `v` at location `[x y]` in matrix `m`."
  [m [x y] v]
  (assoc-in m [y x] v))

(defn set-rect
  "Update the values to `v` at coords given by rectangle starting at `[x y]` of size `dx` x `dy`."
  [m [x y] dx dy v]
  (reduce #(set-element % %2 v)
          m
          (for [y (range y (+ x dy))
                x (range x (+ x dx))]
            [x y])))
