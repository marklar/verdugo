(ns hangman.counts)

(defn incr-key
  "Given a hash-map (hmap) w/ numeric values, increment value at key (k)."
  [hmap k]
  (merge-with + hmap {k 1}))

(defn count-elems
  "Given collection (or str), count appearances for each elem."
  [coll]
  (reduce incr-key {} coll))

(defn char-counts
  "Given strings (strs), create hmap: char -> num-appearances."
  [strs]
  (count-elems (apply concat strs)))

(defn diff-word-counts
   "Given strs, count for each char how many distinct strs it appears in."
   [strs]
   (count-elems (apply concat (map set strs))))

;;----

(defn one-char
  "Update hmap val (vector) at ch: cons str onto it."
  [hmap [ch str]]
  (let [new-vec (cons str (hmap ch))]
    (merge hmap {ch new-vec})))

(defn one-str
  "Add to hmap all [char str] pairs from str."
  [hmap str]
  (let [chars (set str)
	ch-str-pairs (map #(vector % str) chars)]
    (reduce one-char hmap ch-str-pairs)))

(defn char-to-words
  "Given coll of strings, return hash-map: char -> strs."
  [strs]
  (reduce one-str {} strs))
