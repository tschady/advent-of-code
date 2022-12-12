(ns aoc.2022.d07
  (:require
   [aoc.file-util :as f]
   [clojure.string :as str]
   [clojure.zip :as z]
   [com.rpl.specter :as sp]))

(def input (f/read-file "2022/d07.txt"))

;; ## Approach
;; I first did this with Zippers, leaving all that code
;; below in a `comment` for the next time I need to get the rust off.
;;
;; Then I realized the input is in DFS order, so I could just cheat by
;; munging the string to add the right brackets, and eval into a tree.

(defn parse-tree [input]
  (let [open (-> input
                 (str/replace "$ cd .." "]")
                 (str/replace #"\$ cd (.*?)\n" " [")
                 (str/replace #"[^\d \[\]]" ""))
        missing (- (get (frequencies open) \[)
                   (get (frequencies open) \]))]
    (read-string (str open (apply str (repeat missing "]"))))))

(defn dir-sizes [input]
  (->> (parse-tree input)
       (tree-seq vector? identity)
       (filter vector?)
       (map #(sp/select (sp/walker number?) %))
       (map (partial reduce +))
       sort))

(defn part-1 [input]
  (->> (dir-sizes input)
       (filter #(>= 100000 %))
       (reduce +)))

(defn part-2 [input]
  (let [dirs (dir-sizes input)
        space-needed (- 30000000 (- 70000000 (last dirs)))]
    (->> dirs
         (drop-while #(> space-needed %))
         first)))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(comment ;; first zipper approach

(defn make-dir [name] {:type :d :name name :ls '()})

(defn make-file [name size] {:type :f :name name :du size})

(defn target-dir? [node target] (and (= :d (:type (z/node node)))
                                     (= target (:name (z/node node)))))

(defn dir? [node] (= :d (:type node)))

(defn make-zipper [root]
  (z/zipper dir?
            :ls
            (fn [dir files] (assoc dir :ls files))
            root))

(defn cd [node dest]
  (condp = dest
    ".." (z/up node)
    "/" (->> (iterate z/up node)
             (take-while #(not (nil? %)))
             last)
    (->> (z/down node)
         (iterate z/next)
         (drop-while #(and (not (z/end? %))
                           (not (target-dir? % dest))))
         first)))

(defn exec-cmd [fs raw]
  (let [trimmed (str/replace raw #"^\$ " "")
        [a & b] (str/split trimmed #" ")]
    (case a
      "ls"  fs ;; nop
      "cd"  (cd fs (first b))
      "dir" (z/append-child fs (make-dir (first b)))
      (z/append-child fs (make-file (first b) (parse-long a))))))

(defn build-tree [input]
  (let [fs (make-zipper (make-dir "/"))]
    (->> (str/split-lines input)
         rest
         (reduce exec-cmd fs)
         z/root)))

(defn size
  "Returns the size of all files in this directory, recursively"
  [dir]
  (->> dir
       (sp/select (sp/walker #(:du %)))
       (map :du)
       (reduce +)))

(defn dirs-with-sizes [tree]
  (->> (tree-seq dir? :ls tree)
       (filter dir?)
       (map #(assoc % :du (size %)))))

(defn part-1 [input]
  (->> (build-tree input)
       (dirs-with-sizes)
       (filter #(>= 100000 (:du %)))
       (map :du)
       (reduce +)))

(defn part-2 [input]
  (let [dirs (->> (build-tree input)
                  (dirs-with-sizes))
        root-size (:du (first (filter #(= "/" (:name %)) dirs)))
        total 70000000
        target 30000000
        needed (- target (- total root-size))]
    (->> dirs
         (sort-by :du <)
         (drop-while #(> needed (:du %)))
         first
         :du)))
)
