(ns ring-http-cat-status.middleware
  (:import [javax.net.ssl SSLContext X509TrustManager]))

(defn set-socket-factory [conn]
  (let [cert-manager (make-array X509TrustManager 1)
        sc           (SSLContext/getInstance "SSL")]
    (aset cert-manager 0
          (proxy [X509TrustManager][]
            (getAcceptedIssuers [])
            (checkClientTrusted [_ _])
            (checkServerTrusted [_ _])))
    (.init sc nil cert-manager (java.security.SecureRandom.))
    (.setSSLSocketFactory conn (.getSocketFactory sc))))

(defn read-image [url]
  (let [conn (.openConnection (java.net.URL. url))]
    (set-socket-factory conn)
    (.getInputStream conn)))

(defn wrap-cat-http-status [handler]
  (fn [request]
    (let [{:keys [status] :as response} (handler request)]
      (if-not (and status (some #{status} (map (partial + 200) (range 7))))
        {:status status
         :headers {"Content-Type" "image/jpeg"}
         :body (read-image (str "https://http.cat/" status))}
        response))))
