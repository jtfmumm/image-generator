(defproject image-generator "0.1.0-SNAPSHOT"
  :description "Generative art"
  :url "http://github.com/jtfmumm/image-generator"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.5.1"]
  				 [org.clojure/clojurescript "0.0-1978"]
                 [compojure "1.1.5"]
                 [jayq "2.4.0"]
                 [hiccup "1.0.4"]
                 [org.clojure/math.numeric-tower "0.0.2"]
                 [org.clojure/core.async "0.1.242.0-44b1e3-alpha"]
                 [monet "0.1.0-SNAPSHOT"]]
 :source-paths ["src/clj"]
  :plugins [[lein-cljsbuild "0.3.4"]
                 [lein-ring "0.8.7"]]
:cljsbuild
{:builds
 [{:source-paths ["src/cljs"],
   :id "main",
   :compiler
   {:pretty-print true,
    :output-to "resources/public/js/cljs.js",
    :optimizations :simple}}]}
:main musitron.server
:ring {:handler musitron.server/app})