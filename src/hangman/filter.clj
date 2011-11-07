(ns hangman.filter)

(defn lacks-char?
  "Is char-str in str?"
  [str char-str]
  (not (.contains str char-str)))

(defn words-sans-char
  "Given seq of strings (strs), select only those lacking char (char-str)."
  [strs char-str]
  (filter #(lacks-char? % char-str) strs))

(defn of-length
  "Given seq of strings (strs), select only those of provided length (len)."
  [strs len]
  (filter #(= len (.length %)) strs))

(defn matching-pattern
  "Given a pattern "
  [])
