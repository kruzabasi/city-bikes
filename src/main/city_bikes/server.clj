(ns city-bikes.server
  (:require [integrant.core :as ig]
            [city-bikes.router :as router]))

(defn app
  [sys]
  (router/routes sys))

(defmethod ig/init-key ::app
  [_ config]
  (println "\nStarting app..")
  (app config))