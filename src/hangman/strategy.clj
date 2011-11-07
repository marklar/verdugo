(ns hangman.strategy
  (:use [hangman game rand-strategy])
  (:require (clojure.contrib [math :as math]
			     [duck-streams :as duck-streams])))

(def lexicon-file-name "./Hangman_Interview_Attachments/words.txt")
(def lexicon (duck-streams/read-lines lexicon-file-name))

(defn sort-hmap
  "Given hmap, sort by larger val."
  [hmap]
  (reverse (sort-by second hmap)))

(defn next-guess [game]
  (next-random-guess game))
