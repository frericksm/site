(require '[figwheel-sidecar.repl :as r]
         '[figwheel-sidecar.repl-api :as ra])

(ra/start-figwheel!
  {:figwheel-options {} ;;:on-jsload "site.core/start"
   :build-ids ["dev"]
   :all-builds
   [{:id "dev"
     :figwheel true
     :source-paths ["src"]
     :compiler {:main 'site.core
                :asset-path "assets/js/out"
                :output-to "resources/public/assets/js/main.js"
                :output-dir "resources/public/assets/js/out"
                :verbose true}}]})

(ra/cljs-repl)
