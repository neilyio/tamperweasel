(ns user
  (:require
   [shadow.cljs.devtools.api]
   [shadow.cljs.devtools.server]))


;; To be concatenated to the top of the file for loading into script manager.
(def header
  "
// ==UserScript==
// @name         tamperweasel
// @namespace    http://neily.io
// @version      0.1
// @description  try to take over the web!
// @author       neilyio
// @include      http://*
// @include      https://*
// @icon         https://www.google.com/s2/favicons?sz=64&domain=clojurescript.org
// @grant        none
// ==/UserScript==
")

;; Pass as build-hook to concat header and write to final file.
(defn concat-header
  {:shadow.build/stage :flush}
  [build-state]
  (spit "tamperweasel.js"
      (str
       (apply str (->> header (drop 1)))
       (slurp "out/js/tamperweasel.js")))
  (println "Wrote tamperweasel.js")
  build-state)

;; Shadow-cljs options, commonly found in shadow-cljs.edn.
(def options
  {:build-id         :app
   :target           :browser
   :output-dir       "out/js"
   :asset-path       "/js"
   :modules          {:tamperweasel {:entries '[tamperweasel.core]}}
   :compiler-options {:optimizations :advanced
                      :source-map    true}
   :build-hooks '[(user/concat-header)]})

;; Have to start and stop the shadow-cljs server manually.
(shadow.cljs.devtools.server/start!)

(shadow.cljs.devtools.api/compile* options {})

(shadow.cljs.devtools.server/stop!)



