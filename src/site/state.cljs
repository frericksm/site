(ns site.state
  (:require [quile.component :as component]
            [om.next :as om ]))

(defmulti read om/dispatch)

(defmethod read :count [{:keys [state ast] :as env} key _]
  (println "read-1" ast)
  (let [st @state]
    (if-let [[_ value] (find st key)]
      {:value value
       :home ast}
      {:value :not-found})))

(defmethod read :sidebar-navigation [{:keys [state ast] :as env} key _]
  (println "read-2" ast)
  (let [st @state]
    (if-let [[_ value] (find st key)]
      {:value value
       :home ast}
      {:value :not-found})))

(defmulti mutate om/dispatch)

(defmethod mutate 'increment [{:keys [state ast] :as env} _ params]
  (println "mutate-1" ast)
  {:value [:count]
   :action #(swap! state update-in [:count] inc)})

(defmethod mutate 'app/click [{:keys [state ast] :as env} _ item]
  (println "mutate-2" ast)
  {:value [{:sidebar-navigation [:active-item]}]
   :action #(swap! state assoc-in [:sidebar-navigation :active-item] (:name item))})

(defonce state-atom 
  (atom {:count 0
         :sidebar-navigation
         {:active-item "Übersicht"
          :items [{:icon "pe-7s-home" 
                   :name "Übersicht"}
                  {:icon "pe-7s-news-paper" 
                   :name "Notizen"}
                  {:icon "pe-7s-news-paper" 
                   :name "Minecraft"}]}}))

(defrecord State []
  ;; Implement the Lifecycle protocol
  component/Lifecycle

  (start [this]
    (println "Starting State")
    (assoc this :app-state state-atom))

  (stop [this]
    (println "Stopping State!")
    (dissoc this :app-state)))

(defn new-state []
  (map->State {}))
