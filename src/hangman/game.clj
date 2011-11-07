(ns hangman.game)

(def mystery-letter \-)

(defn mk-game [word max-wrong-guesses]
  ;; (let [w (.toUpperCase word)]
  (let [w word]
    {:secret-word        w
     :max-wrong-guesses  max-wrong-guesses
     :wrong-words        #{}
     :wrong-letters      #{}
     :right-letters      #{}
     :guessed-so-far     (apply str (repeat (.length w) mystery-letter))
     }))

(defn num-wrong-guesses-made [game]
  (+ (count (:wrong-letters game))
     (count (:wrong-words game))))

(defn num-wrong-guesses-remaining [game]
  (- (:max-wrong-guesses game)
     (num-wrong-guesses-made game)))

(defn lost? [game]
  (< (num-wrong-guesses-remaining game) 0))

(defn won? [game]
  (= (:secret-word game) (:guessed-so-far game)))

(defn game-status [game]
  (cond (won?  game) :game_won
	(lost? game) :game_lost
		     :else        :keep-guessing))

(defn keep-guessing? [game]
  (= (game-status game) :keep-guessing))

(defn already-guessed-char?
  [game char]
  (let [used? #(contains? (get game %) char)]
    (or (used? :wrong-letters)
	(used? :right-letters))))

(defn valid-char-guess? [game char]
  (not (already-guessed-char? game char)))

(defn valid-word-guess? [game word]
  (not (contains? (:wrong-words game) word)))

(defn good-letter-guess? [game char]
  (contains? (set (:secret-word game)) char))

(defn update-new-so-far
  "Update guessed-so-far (String) wherever char appears."
  [game char]
  (let [pairs (map vector (:guessed-so-far game) (:secret-word game))]
    (apply str (map (fn [[gsf-ch sw-ch]]
		      (if (= char sw-ch) sw-ch gsf-ch))
		    pairs))))

(defn guess-letter
  "Returns 'updated' game."
  [game char]
  ;; conditions
  {:pre [(keep-guessing? game)
	 (valid-char-guess? game char)]}
  ;; body
  (conj game
	(if (good-letter-guess? game char)
	  {:right-letters  (conj (:right-letters game) char)
	   :guessed-so-far (update-new-so-far game char)}
	  {:wrong-letters (conj (:wrong-letters game) char)})))

(defn guess-word
  "Returns 'updated' game."
  [game guess]
  ;; conditions
  {:pre [(keep-guessing? game)
	 (valid-word-guess? game guess)]}
  ;; body
  (conj game
	(if (= guess (:secret-word game))
	  {:guessed-so-far guess}
	  {:wrong-words (cons guess (:wrong-words game))})))

(defn current-score [game]
  (if (lost? game)
    25
    (+ (num-wrong-guesses-made game)
       (count (:right-letters game)))))

(defn game-status-str [game]
  (.toUpperCase (apply str (rest (str (game-status game))))))

(defn show-game [game]
  (str (.toUpperCase (:guessed-so-far game))
       "; score="  (str (current-score game))
       "; status=" (game-status-str game)))
