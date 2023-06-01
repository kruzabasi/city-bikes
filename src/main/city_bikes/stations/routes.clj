(ns city-bikes.stations.routes
  (:require [city-bikes.stations.handlers :refer [get-all-stations get-single-station]]))

(def routes
  ["/station"
   [""
    {:get {:handler    get-all-stations
           :responses  {201 {:body nil?}}
           :parameters {}
           :summary    "Fetch list of Stations"}}]
   ["/:station-id"
    [""
     {:get {:handler    get-single-station
            :responses  {201 {:body nil?}}
            :parameters {:path {:station-id string?}}
            :summary    "Fetch a Single Stations Data"}}]]])