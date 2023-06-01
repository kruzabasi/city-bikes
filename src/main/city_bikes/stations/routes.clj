(ns city-bikes.stations.routes
  (:require [city-bikes.stations.handlers :refer [get-all-stations]]))

(def routes
  ["/station"
   [""
    {:get {:handler     get-all-stations
           :responses  {201 {:body nil?}}
           :parameters {}
           :summary    "Fetch list of Stations"}}]])