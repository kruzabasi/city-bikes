(ns city-bikes.schema)

(def station-schema 
  [{:db/ident       :station/id
    :db/valueType   :db.type/long
    :db/cardinality :db.cardinality/one
    :db/unique      :db.unique/identity
    :db/doc         "Station ID"}
   
   {:db/ident       :station/name
    :db/valueType   :db.type/string
    :db/cardinality :db.cardinality/one
    :db/doc         "Station Name"}
   
   {:db/ident       :station/address
    :db/valueType   :db.type/string
    :db/cardinality :db.cardinality/one
    :db/doc         "Station Address"}
   
   {:db/ident       :station/city
    :db/valueType   :db.type/string
    :db/cardinality :db.cardinality/one
    :db/doc         "Station City Name"}
   
   {:db/ident       :station/operator
    :db/valueType   :db.type/string
    :db/cardinality :db.cardinality/one
    :db/doc         "Station Operators Name"}
   
   {:db/ident       :station/capacity
    :db/valueType   :db.type/long
    :db/cardinality :db.cardinality/one
    :db/doc         "Station Max Capacity"}
   
   {:db/ident       :station/coord.x
    :db/valueType   :db.type/double
    :db/cardinality :db.cardinality/one
    :db/doc         "Station Longitude"}
   
   {:db/ident       :station/coord.y
    :db/valueType   :db.type/double
    :db/cardinality :db.cardinality/one
    :db/doc         "Station Latitude"}])

(def trip-schema
  [{:db/ident       :trip/departure-time
    :db/valueType   :db.type/instant
    :db/cardinality :db.cardinality/one
    :db/doc         "Trip departure time"}
   
   {:db/ident       :trip/return-time
    :db/valueType   :db.type/instant
    :db/cardinality :db.cardinality/one
    :db/doc         "Trip return time"}
   
   {:db/ident       :trip/departure-station
    :db/valueType   :db.type/ref
    :db/cardinality :db.cardinality/one
    :db/doc         "Trip departure Station"}
   
   {:db/ident       :trip/return-station
    :db/valueType   :db.type/ref
    :db/cardinality :db.cardinality/one
    :db/doc         "Trip return Station"}
   
   {:db/ident       :trip/distance
    :db/valueType   :db.type/long
    :db/cardinality :db.cardinality/one
    :db/doc         "Distance of Trip in meters"}
   
   {:db/ident       :trip/duration
    :db/valueType   :db.type/long
    :db/cardinality :db.cardinality/one
    :db/doc         "Duration of Trip in seconds"}])