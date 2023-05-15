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