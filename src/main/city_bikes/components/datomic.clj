(ns city-bikes.components.datomic
  (:require [integrant.core :as ig]
            [datomic.api :as d]))

(defmethod ig/init-key ::db
  [_ config]
  (println "\nStarting db...")
  (let [db-uri (:db-uri config)
        _      (d/create-database db-uri)
        conn   (d/connect db-uri)]
    (println "\ndatomic db connected: " conn)
    (assoc config :conn conn)))

(defmethod ig/halt-key! ::db
  [_ config]
  (d/release (:conn config))
  (println "\nReleased db conn..."))