(ns hangman.main
  (:use [hangman game strategy]))

;; Must be able to guess a word (if that's a good idea).

(defn make-guess
  "Makes guess.  Returns 'updated' game."
  [game guess]
  (let [g (guess-letter game guess)]
    ;; (println guess)
    (println (show-game g))
    g))

;; It makes sense to guess a WORD
;; if doing so is equally informative.
;; (Because, if right, doesn't count.)

(defn play [game]
  "Keeps making guesses until fails."
  (loop [strategy (mk-strategy game)]
    (if-let [strat (next-guess strategy)]
      (do (println (show-strategy strat))
	  (recur strat))
      (println "done"))))
    
(def words
     ["comaker"
      "cumulate"
      "eruptive"
      "factual"
      "monadism"
      "mus"
      "nagging"
      "oses"
      "remembered"
      "spodumenes"
      "stereoisomers"
      "toxics"
      "trichromats"
      "triose"
      "uniformed"])

(doseq [w words]
  (play (mk-game w 5)))
