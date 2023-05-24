(ns user
  (:require [integrant.core :as ig]
            [integrant.repl :as ig-repl]
            [integrant.repl.state :as state]
            [clojure.java.io :as io]
            [clojure.data.csv :as csv]
            [clojure.instant :as instant]
            [datomic.api :as d]))

(ig-repl/set-prep!
 (fn []
   (let [config (-> "config.edn"
                    io/resource
                    slurp
                    ig/read-string)]
     (ig/load-namespaces config)
     config)))

(defn start-server
  []
  (ig-repl/go))

(defn stop-server
  []
  (ig-repl/halt))

(defn conn
  []
  (-> integrant.repl.state/system
      :city-bikes.components.datomic/db
      :conn))

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

(defn db-add!
  [conn tx-data]
  (d/transact conn tx-data))


(defmulti load-data
  "(load-data :entity data)
   data must be [vector of strings] matching valid entity method"
  (fn [entity _ _] entity))

(defmethod load-data :stations
  [_ conn [_ id _ _ s-name _ adress _ city operator capacity x y]]
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
  [_ conn [departure return ds-id _ rs-id _ dist duration]]
  (when (and (>= (str->long dist) 10) (>= (str->long duration) 10))
    (db-add! conn
             [{:trip/departure-time    (str->inst departure)
               :trip/return-time       (str->inst return)
               :trip/departure-station [:station/id (str->long ds-id)]
               :trip/return-station    [:station/id (str->long rs-id)]
               :trip/distance          (str->long dist)
               :trip/duration          (str->long duration)}])))

(defn import-csv-data ;; (import-csv-data :trips file)
  [entity file]
  (let [data (read-csv-data file)
        conn (conn)]
    (prn (str "Importing " (count data) " Items Into " entity))
    (doseq [row data]
      (try
        (load-data entity conn row)
        (catch NumberFormatException _
          (prn "\n Error Loading Trip: " row))))))

(defn initiate-schema [schema-data]
  (let [conn (conn)]
    (d/transact conn schema-data)))