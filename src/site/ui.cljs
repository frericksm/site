(ns site.ui
  (:require [quile.component :as component]
            [goog.dom :as gdom]
            [om.next :as om :refer-macros [defui]]
            [om.dom :as dom]
            [site.state :as sis]
            [site.remote :as sir]
            [sablono.core :as html :refer-macros [html]]))

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
                      #js {:className "btn btn-info"
                           :onClick
                           (fn [e] (om/transact! this '[(increment)]))}
                      "Click me!")))))

(defui SideBarNavigation
  static om/IQuery
  
  (query [this]
         '[{:sidebar-navigation [:active-item :items]}])

  Object
  (render [this]
          (let [{:keys [sidebar-navigation]} (om/props this)] 
            (html
             [:ul {:class "nav"}
              (for [item (:items sidebar-navigation)]
                (let [n (:name item)
                      ai (:active-item sidebar-navigation)]
                  [:li {:key n
                        :class (if (= n ai) "active")}
                   [:a {:on-click 
                        (fn [e] (om/transact! this `[(app/click ~item)]))} 
                    [:i {:class (:icon item)
                         :key "i"}] 
                    [:p {:key "p"} (:name item)]]]))]))))

(defrecord UserInterface [state]
  ;; Implement the Lifecycle protocol
  component/Lifecycle

  (start [this]
    (println ";; Starting UI") 
    (let [reconciler1
          (om/reconciler
           {:state (:app-state state)
            :send sir/send
            :remotes [:home]
            :parser (om/parser {:read sis/read :mutate sis/mutate})})
          reconciler2
          (om/reconciler
           {:state (:app-state state)
            :send sir/send
            :remotes [:home]
            :parser (om/parser {:read sis/read :mutate sis/mutate})})]
      (om/add-root! reconciler1
                    Counter (gdom/getElement "chartPreferences"))
      (om/add-root! reconciler2
                    SideBarNavigation (gdom/getElement "sidebar-navigation"))
      (assoc this :reconciler [reconciler1 reconciler2])))


  (stop [this]
    (println ";; Stopping UI")
    (dissoc this :reconciler))) 

(defn new-ui []
  (map->UserInterface {}))
