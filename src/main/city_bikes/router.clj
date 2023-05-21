(ns city-bikes.router
  (:require [city-bikes.middleware :as mw]
            [reitit.ring :as ring]
            [ring.util.response :as rr]
            [muuntaja.core :as m]
            [reitit.coercion.spec :as coercion-spec]
            [reitit.dev.pretty :as pretty]
            [reitit.ring.coercion :as coercion]
            [reitit.ring.middleware.muuntaja :as muuntaja]
            [reitit.ring.spec :as rs]
            [reitit.ring.middleware.dev :as dev]))

(defn router-config
  [sys]
  {:validate rs/validate
   :reitit.middleware/transform dev/print-request-diffs
   :exception pretty/exception
   :data {:sys sys
          :coercion coercion-spec/coercion
          :muuntaja m/instance
          :middleware [muuntaja/format-middleware
                       coercion/coerce-request-middleware
                       coercion/coerce-response-middleware
                       mw/wrap-sys]}})

(defn hello-handler
  [{{{:keys [conn]} :datomic} :sys}]
  (prn "Conn " conn)
  (rr/response {:hello ["Hekko MF"]}))

(defn routes
  [sys]
  (ring/ring-handler
   (ring/router
    ["/hello"
     {:get {:handler   hello-handler
            :responses {201 {:body nil?}}
            :summary   "Test"}}]
    (router-config sys))))
