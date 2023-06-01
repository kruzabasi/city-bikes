(ns city-bikes.router
  (:require [city-bikes.middleware :as mw]
            [reitit.ring :as ring]
            [muuntaja.core :as m]
            [reitit.coercion.spec :as coercion-spec]
            [reitit.dev.pretty :as pretty]
            [reitit.ring.coercion :as coercion]
            [reitit.ring.middleware.muuntaja :as muuntaja]
            [reitit.ring.spec :as rs]
        ;;    [reitit.ring.middleware.dev :as dev]
            
            [city-bikes.stations.routes :as stations]))

(defn router-config
  [sys]
  {:validate rs/validate
  ;; :reitit.middleware/transform dev/print-request-diffs
   :exception pretty/exception
   :data {:sys sys
          :coercion coercion-spec/coercion
          :muuntaja m/instance
          :middleware [muuntaja/format-middleware
                       coercion/coerce-request-middleware
                       coercion/coerce-response-middleware
                       mw/wrap-sys]}})

(defn routes
  [sys]
  (ring/ring-handler
   (ring/router
    ["/v1"
     stations/routes]
    (router-config sys))))
