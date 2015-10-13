(ns site.state
  (:require [quile.component :as component]))

(defn read [{:keys [state] :as env} key params]
  (let [st @state]
    (if-let [[_ value] (find st key)]
      {:value value}
      {:value :not-found})))

(defn mutate [{:keys [state] :as env} key params]
  (if (= 'increment key)
    {:value [:count]
     :action #(swap! state update-in [:count] inc)}
    {:value :not-found}))

(defonce s (atom {:count 0}))

(defrecord State []
  ;; Implement the Lifecycle protocol
  component/Lifecycle

  (start [this]
    (println "Starting State")
    (assoc this :app-state s))

  (stop [this]
    (println "Stopping State!")
    (dissoc this :app-state)))

(defn new-state []
  (map->State {}))
