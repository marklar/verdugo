(defproject hangman "0.0.1"
  :description "Strategy for playing Hangman."
  :url "localhost:8080"
  :main hangman.main
  :tasks []
  :dependencies
  [;; clojure
   [org.clojure/clojure "1.2.0"]
   [org.clojure/clojure-contrib "1.2.0"]
   ;; incanter
   [incanter "1.2.3"]
   ]

  :dev-dependencies
    [[autodoc "0.7.1" :exclusions [org.clojure/clojure
                                   org.clojure/clojure-contrib]]]
  :autodoc
    {:name "hangman"
     :description "strategy for playing Hangman"
     :copyright "Copyright 2011 Mark Wong-VanHaren"
     :root "."
     :source-path ""
     :web-src-dir ""
     :web-home ""
     :output-path "autodoc"
     :namespaces-to-document ["hangman"]
     :trim-prefix "hangman."
     :load-except-list [#"/example/" #"/test/" #"project\.clj"]})
