(ns site.system 
  (:require  [quile.component :as component]
             [site.ui :as ui]
             [site.state :as state]))

(defn system [config-options]
  (component/system-map
   :state (state/new-state)
   :ui    (component/using
           (ui/new-ui)
           [:state])))
