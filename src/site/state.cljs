(ns site.state
  (:require [quile.component :as component]
            [om.next :as om ]))

(defmulti read om/dispatch)

(defmethod read :count [{:keys [state] :as env} key _]
  (let [st @state]
    (if-let [[_ value] (find st key)]
      {:value value}
      {:value :not-found})))

(defmethod read :sidebar-navigation [{:keys [state] :as env} key _]
  (let [st @state]
    (if-let [[_ value] (find st key)]
      {:value value}
      {:value :not-found})))

(defmulti mutate om/dispatch)

(defmethod mutate 'increment [{:keys [state] :as env} _ params]
  {:value [:count]
   :action #(swap! state update-in [:count] inc)})

(defmethod mutate 'app/click [{:keys [state] :as env} _ item]
  {:value [{:sidebar-navigation [:active-item]}]
   :action #(swap! state assoc-in [:sidebar-navigation :active-item] (:name item))})

(defonce state-atom 
  (atom {:count 0
         :sidebar-navigation
         {:active-item "Übersicht"
          :items [{:icon "pe-7s-home" 
                   :name "Übersicht"}
                  {:icon "pe-7s-news-paper" 
                   :name "Notizen"}]}}))

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
