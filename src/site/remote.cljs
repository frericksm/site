(ns site.remote
  (:require [quile.component :as component]
            [om.next :as om ]))


(defn send [remote-to-msgs callback-fn]
  (println "send" remote-to-msgs)
   
  (cond (not (nil? (:home remote-to-msgs))) (callback-fn [{:count 66}] )
          true (callback-fn [{:sidebar-navigation {:active-item "ff" :items []}}])
        ))
