(ns city-bikes.server
  (:require [integrant.core :as ig]
            [ring.adapter.jetty :as jetty]
            [ring.util.response :as rr])
  (:import  [org.eclipse.jetty.server Server]))

(def config 
  {:adapter/jetty {:port 8080 :handler (ig/ref :handler/greet)}
   :handler/greet {:name "City Bike"}})

(defmethod ig/init-key :handler/greet
 [_ opts]
 (fn [_]
   (rr/response "Hello City-Bike")))

(defmethod ig/init-key :adapter/jetty
  [_ {:keys [handler port]}]
  (jetty/run-jetty handler {:port  port
                            :join? false}))

(defmethod ig/halt-key! :adapter/jetty 
  [_ ^Server server]
  (.stop server))