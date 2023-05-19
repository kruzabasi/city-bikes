(ns city-bikes.server
  (:require [integrant.core :as ig]))

(defmethod ig/init-key ::app
  [_ config]
  (println "\nStarting app..")
  (comment "Handle Routes Here!"))