(ns site.core
  (:require  [quile.component :as component]
             [site.system :as system]))

(enable-console-print!)

(defn start []
  (component/start  (system/system {})))

(start)

