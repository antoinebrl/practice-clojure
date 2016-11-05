(defproject syntaxe "0.1.0-SNAPSHOT"
  :description "Learning Clojure syntaxe"
  :url "https://github.com/antoinebrl/practice-clojure"
  :license {}
  :dependencies [[org.clojure/clojure "1.8.0"]]
  :main ^:skip-aot syntaxe.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
