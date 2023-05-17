(ns city-bikes.schema)

(def station-schema 
  [{:db/ident       :station/id
    :db/valueType   :db.type/long
    :db/cardinality :db.cardinality/one
    :db/doc         "Station ID"}
   
   {:db/ident       :station/name
    :db/valueType   :db.type/string
    :db/cardinality :db.cardinality/one
    :db/doc         "Station Name"}])