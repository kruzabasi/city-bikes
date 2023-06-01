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

(defn get-single-station
  [{:keys [parameters sys]}]
  (let [conn       (-> sys :datomic :conn)
        station-id (-> parameters :path :station-id Long/parseLong)]
    (rr/response {:data
                  (d/q '[:find (pull ?e [*])
                         :in $ ?station-id
                         :where [?e :station/id ?station-id]]
                       (d/db conn)
                       station-id)})))