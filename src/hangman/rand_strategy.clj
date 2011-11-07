(ns hangman.rand-strategy
  (:use [hangman game])
  (:require (clojure.contrib [math :as math])))

(def letters (seq "abcdefghijklmnopqrstuvwxyz"))
(defn random-letter []
  (let [idx (math/floor (* 26 (rand)))]
    (nth letters idx)))

(defn random-unused-letter [game]
  (loop []
    (let [ch (random-letter)]
      (if (valid-char-guess? game ch)
	ch
	(recur)))))

(defn next-random-guess [game]
  (if (keep-guessing? game)
    (random-unused-letter game)
    nil))
