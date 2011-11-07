(ns hangman.main
  (:use [hangman counts filter game strategy]))

(defn make-guess
  "Makes guess.  Returns 'updated' game."
  [game guess]
  (let [g (guess-letter game guess)]
    (println guess)
    (println (show-game g))
    g))

;; (def game (mk-game "foolish" 4))
;; (reduce make-guess game guesses)

(defn play [game]
  (loop [game game]
    (if-let [guess (next-guess game)]
      (recur (make-guess game guess))
      (println "done"))))

(play (mk-game "foolish" 4))
