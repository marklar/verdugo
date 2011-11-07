(ns hangman.strategy
  (:use [hangman game counts])
  (:require (clojure.contrib [duck-streams :as duck-streams])))

(def lexicon-file-name "./words.txt")
(def lexicon (duck-streams/read-lines lexicon-file-name))

(defn sort-hmap
  "Given hmap, sort by larger val."
  [hmap]
  (reverse (sort-by second hmap)))

;;--------------

(defn secret-word-to-re-str
  "Turns '-pp--' into /^[a-z]pp[a-z][a-z]$/"
  [word]
  (let [mk-re (fn [c] (if (= c mystery-letter) "[a-z]" (str c)))]
    (str \^
	 (apply str (map mk-re word))
	 \$)))

;; Not a candidate if already guessed.  :wrong-words
(defn candidates
  [game strs]
  (let [re (re-pattern (secret-word-to-re-str (game :guessed-so-far)))]
    (filter #(not (contains? (:wrong-words game) %))
	    (filter #(re-find re %) strs))))

;; Now, we just find the safest guess.
;; But we should consider which guess provides the most info.
;;
;; Also, if there are fewer candidate-words than candidate-chars,
;; guess a word instead.
;; Rather, if guessing a word is equally informative as guessing a char,
;; prefer guessing a word.

(defn next-naive-guess
  "Return: Char or nil."
  [game]
  (let [word-cands (candidates game lexicon)
	_ (println (str "num char candidates: " (count word-cands)))
	best-chars (keys (sort-hmap (diff-word-counts word-cands)))]
    (first (filter #(valid-char-guess? game %) best-chars))))

;;--------------

;;
;; Return: Char|nil, new Strategy.
;;

(defn next-guess
  "Return: Char or nil."
  [game]
  (if (game-done? game)
    nil
    (next-naive-guess game)))
