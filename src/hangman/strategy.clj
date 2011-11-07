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
  (let [char-re (fn [c] (if (= c mystery-letter) "[a-z]" (str c)))]
    (str \^
	 (apply str (map char-re word))
	 \$)))

(defn mk-re
  [game]
  (re-pattern (secret-word-to-re-str (game :guessed-so-far))))
  
(defn word-candidates
  [game words]
  (let [re (mk-re game)]
    (filter #(not (contains? (:wrong-words game) %))
	    (filter #(re-find re %) words))))

(defn mk-strategy
  "Return initial strategy for a game."
  [game]
  {:game game
   :words (word-candidates game lexicon)})

;; Now, we just find the safest guess.
;; But we should consider which guess provides the most info.
;;
;; Also, if there are fewer candidate-words than candidate-chars,
;; guess a word instead.
;; Rather, if guessing a word is equally informative as guessing a char,
;; prefer guessing a word.

(defn guess-ch
  [strategy ord-chars]
  (first (filter #(valid-char-guess? (:game strategy) %) ord-chars)))

(defn next-naive-guess
  "Return 'updated' strategy."
  [strategy]
  (println (str "num char candidates: " (count (:words strategy))))
  (let [ord-chars (keys (sort-hmap (diff-word-counts (:words strategy))))
	ch (guess-ch strategy ord-chars)
	new-game (guess-letter (:game strategy) ch)]
    (mk-strategy new-game)))

(defn next-guess
  "Return 'updated' strategy."
  [strategy]
  (if (game-done? (:game strategy))
    nil
    (next-naive-guess strategy)))

(defn show-strategy
  [strategy]
  (show-game (:game strategy)))
