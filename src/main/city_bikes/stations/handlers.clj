(ns city-bikes.stations.handlers
  (:require [datomic.api :as d]
            [ring.util.response :as rr]))

(defn get-all-stations
  [{{{:keys [conn]} :datomic} :sys}]
  (rr/response {:stations
                (d/q '[:find (pull ?e
                                   [:station/id
                                    :station/address
                                    :station/name])
                       :where [?e :station/id]]
                     (d/db conn))}))