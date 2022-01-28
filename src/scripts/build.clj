(ns scripts.build
  (:require
   [shadow.cljs.devtools.api]))

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

(defn build []
  (shadow.cljs.devtools.api/release :release)
  (spit "dist/tamperweasel.js"
      (str
       (apply str (->> header (drop 1)))
       (slurp "compiled/js/tamperweasel.js"))))
