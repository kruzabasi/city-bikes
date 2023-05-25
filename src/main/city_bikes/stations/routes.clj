(ns city-bikes.stations.routes
  (:require [datomic.api :as d]
            [ring.util.response :as rr]))

(defn get-all-stations
  [{{{:keys [conn]} :datomic} :sys}]
  (rr/response {:stations
                (d/q '[:find (pull ?station [:station/id
                                             :station/address
                                             :station/name])
                       :where [?station :station/id]]
                     (d/db conn))}))

(def routes
  ["/station"
   [""
    {:get {:handler     get-all-stations
           :responses  {201 {:body nil?}}
           :parameters {}
           :summary    "Fetch list of Stations"}}]])