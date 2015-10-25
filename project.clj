(defproject site "0.1.0-SNAPSHOT"
  :description "My site"
  :dependencies [[org.clojure/clojure "1.7.0"]
                 [org.clojure/clojurescript "1.7.145"]
                 [org.clojure/core.async "0.1.346.0-17112a-alpha"]
                 [org.omcljs/om "1.0.0-alpha8"]
                 [quile/component-cljs "0.2.4" :exclusions [org.clojure/clojure]]
                 [sablono "0.3.6"]

                 [figwheel-sidecar "0.4.0" :scope "provided"]])
