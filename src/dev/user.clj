(ns user
  (:require [integrant.repl :as ig-repl]
            [city-bikes.server :as server]
            [clojure.java.io :as io]
            [clojure.data.csv :as csv]
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

(defn read-csv-data
  "Reads a csv file and returns the data without headers"
  [file]
  (let [data (-> file io/resource slurp csv/read-csv rest)]
    data))

(comment
  (def db-uri "datomic:dev://localhost:4334/city-bike")


(defn create-db
  []
  (d/create-database db-uri))

  (def conn (d/connect db-uri))


  (defn initiate-schema [schema-data]
    (d/transact conn schema-data)))