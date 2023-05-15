(ns user
  (:require [integrant.repl :as ig-repl]
            [city-bikes.server :as server]
            [datomic.api :as d]))

(ig-repl/set-prep!
 (fn []
   server/config))

(defn start-server
  []
  (ig-repl/go))

(defn stop-server
  []
  (ig-repl/halt))

(def db-uri "datomic:dev://localhost:4334/city-bike")

(comment 
 (defn create-client
  []
  (d/client {:server-type :dev-local
             :system "dev"})))

(defn create-db
  []
  (d/create-database db-uri))

(def conn (d/connect db-uri))