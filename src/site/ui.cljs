(ns site.ui
  (:require [quile.component :as component]
            [goog.dom :as gdom]
            [om.next :as om :refer-macros [defui]]
            [om.dom :as dom]
            [site.state :as state]))

(defui Counter
  static om/IQuery
  (query [this]
    [:count])
  Object
  (render [this]
    (let [{:keys [count]} (om/props this)]
      (dom/div nil
        (dom/span nil (str "Count: " count))
        (dom/button
          #js {:onClick
               (fn [e] (om/transact! this '[(increment)]))}
          "Click me!")))))

(defrecord UserInterface [state]
  ;; Implement the Lifecycle protocol
  component/Lifecycle

  (start [this]
    (println ";; Starting UI") 
    (let [reconciler
          (om/reconciler
           {:state (:app-state state)
            :parser (om/parser {:read state/read :mutate state/mutate})})]
      (om/add-root! reconciler
                    Counter (gdom/getElement "app"))
      (assoc this :reconciler reconciler)))

  (stop [this]
    (println ";; Stopping UI")
    (dissoc this :reconciler)))

(defn new-ui []
  (map->UserInterface {}))
