(ns user
  (:require [integrant.repl :as ig-repl]
            [city-bikes.server :as server]
            [clojure.java.io :as io]
            [clojure.data.csv :as csv]
            [clojure.instant :as instant]
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

(defn str->inst
  [string]
  (instant/read-instant-date string))

(defn str->long
  [string]
  (Long/parseLong string))

(defn str->double
  [string]
  (Double/parseDouble string))

(defn read-csv-data
  "Reads a csv file and returns the data (without headers)
   as a list of vectors"
  [file]
  (let [data (-> file io/resource slurp csv/read-csv rest)]
    data))

(comment
  
  (def db-uri "datomic:dev://localhost:4334/city-bike")
  (def conn (d/connect db-uri))

  (defn db-add!
    [conn tx-data]
    (d/transact conn tx-data)))


(defmulti load-data
  "(load-data :entity data)
   data must be [vector of strings] matching valid entity method"
  (fn [entity _] entity))

(defmethod load-data :stations
  [_ [_ id _ _ s-name _ adress _ city operator capacity x y]]
  (db-add! conn
           [{:station/id       (str->long id)
             :station/name     s-name
             :station/address  adress
             :station/city     city
             :station/operator operator
             :station/capacity (str->long capacity)
             :station/coord.x  (str->double x)
             :station/coord.y  (str->double y)}]))

(defmethod load-data :trips
  [_ [departure return ds-id _ rs-id _ dist duration]]
  (db-add! conn
           [{:trip/departure-time    (str->inst departure)
             :trip/return-time       (str->inst return)
             :trip/departure-station [:station/id (str->long ds-id)]
             :trip/return-station    [:station/id (str->long rs-id)]
             :trip/distance          (str->long dist)
             :trip/duration          (str->long duration)}]))

(defn import-csv-data ;; (import-csv-data :trips file)
  [entity file]
  (let [data (read-csv-data file)]
    (prn (str "Importing " (count data) " Items Into " entity))
    (for [row data]
      (load-data entity row))))


(comment
  
  (defn create-db
    []
    (d/create-database db-uri))

  (defn initiate-schema [schema-data]
    (d/transact conn schema-data)))