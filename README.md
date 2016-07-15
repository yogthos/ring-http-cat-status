# ring-http-cat-status

A Clojure library designed to serve HTTP status images from [https://http.cat/](https://http.cat/).

## Usage


[![Clojars Project](https://img.shields.io/clojars/v/ring-http-cat-status.svg)](https://clojars.org/ring-http-cat-status)

The library provides a `wrap-cat-http-status` function that will return an
image from [https://http.cat/](https://http.cat/) based on the response status code
for any response that's not in 200 range.

```clojure
(myapp.middleware
  (:require [ring-http-cat-status.middleware :refer [wrap-cat-http-status]]))

(defn bad-hanlder [request]
  {:status 500
   :headers {"Content-Type" "text/plain"}
   :body "oops"})

(wrap-cat-http-status bad-handler)
```

## License

Copyright © 2016 Dmitri Sotnikov

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
