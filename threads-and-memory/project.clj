(defproject threads-and-memory "0.1.0-SNAPSHOT"
  :description "Learning threads and memory"
  :url "https://github.com/antoinebrl/practice-clojure"
  :license {}
  :dependencies [[org.clojure/clojure "1.8.0"]]
  :main ^:skip-aot threads-and-memory.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
