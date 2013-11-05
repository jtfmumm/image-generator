(ns async.core
    (:require-macros [cljs.core.async.macros :refer [go] :as a])
    (:require 
     		[cljs.core.async :refer [put! timeout chan map<]]
     		[goog.events :as events]
	       	[jayq.core :as jq]
	       	[monet.canvas :as mo])
   	(:use [jayq.core :only [$]]))

(def $colorchange ($ :#colorchange))

(defn addstuff []
      (jq/css $colorchange {:color "blue"}))

(jq/bind ($ :#colorchange) :click addstuff)

(jq/bind ($ :#clickhere) :click (fn [evt] (js/alert "Clicked!!")))


(def $drawing ($ :#drawing))

(def ctx (mo/get-context (.get $drawing 0) "2d"))

(mo/stroke-style ctx "#eee")

(defn draw-line [startx starty finishx finishy]
	(do
		(mo/move-to ctx startx starty)
		(mo/line-to ctx finishx finishy)
		(mo/stroke ctx)))

(defn draw-rect [ctx x y w h]
  (mo/begin-path ctx)
  (. ctx (rect x y w h))
  (mo/close-path ctx)
  (mo/fill ctx)
  ctx)

(defn draw-point [x y]
	(draw-rect ctx x y 1 1))

;(draw-line 100 100 300 300)
;(draw-line 0 0 30 30)
(draw-point 10 10)
(draw-point 200 250)

(defn pick-rand [low high]
	(+
	  (* 
	    (- high low) 
	    (rand 1))
	  low))

(defn random-draw []
	(loop [counter 10000]
		(if (> counter 0)
			(do
				(draw-point (rand-int 500) (rand-int 500)); (rand-int 500) (rand-int 500))
				(recur (dec counter))))))

(defn draw-burst [x y span density]
	(loop [counter density]
		(if (> counter 0)
			(do
				(draw-point (pick-rand (- x span) (+ x span)) 
							(pick-rand (- y span) (+ y span)))
				(recur (dec counter))))))

;(draw-burst 50 50 10 50)

(defn events [el type]
  (let [out (chan)]
    (events/listen el type
      (fn [e] (put! out e)))
    out))

(defn pos [e]
  [(.-clientX e) (.-clientY e)])

(def curpoint (atom #{}))


(reset! curpoint [0, 0])

(defn sample-rate [x sr]
	(/ x sr))

(let [c (map< pos (events js/window "mousemove"))]
  (go (while true
  			(<! (timeout 50))
  			(draw-line (first (deref curpoint)) 
  					   (+ 250 (* 100 (.sin js/Math (/ (first (deref curpoint)) 50))))
  					   (inc (first (deref curpoint)))
  					   (+ 250 (* 100 (.sin js/Math (/ (inc (first (deref curpoint))) 50)))))
  			;(draw-point (first (deref curpoint)) (* 250 (second (deref curpoint))))
  			;(.log js/console [(first (deref curpoint)), (* 250 (second (deref curpoint)))])
  			(reset! curpoint [(mod (+ 2 (first (deref curpoint))) 500), (second (deref curpoint))]);, (.sin js/Math (first (deref curpoint)))])
  			(.log js/console (first (deref curpoint)));(deref curpoint))
  		;(draw-burst (mod (first (<! c)) 500) (- (mod (second (<! c)) 500) 90) (rand-int 20) (rand-int 100))
  		;(draw-point (mod (first (<! c)) 500) (mod (second (<! c)) 500))
        ;(.log js/console (<! c)))))
		)))


(let [c (map< pos (events js/window "mousemove"))]
  (go (while true
  			(<! (timeout 25))
  			(draw-point (second (deref curpoint)) 
  					   (+ 100 (* 250 (.sin js/Math (/ (second (deref curpoint)) (pick-rand 80 120))))))
  					   ;(inc (second (deref curpoint)))
  					   ;(+ 100 (* 250 (.sin js/Math (/ (inc (second (deref curpoint))) (pick-rand 80 120))))))
  			;(draw-point (first (deref curpoint)) (* 250 (second (deref curpoint))))
  			;(.log js/console [(first (deref curpoint)), (* 250 (second (deref curpoint)))])
  			(reset! curpoint [(first (deref curpoint)), (mod (inc (second (deref curpoint))) 500)]);, (.sin js/Math (first (deref curpoint)))])
  			(.log js/console (first (deref curpoint)));(deref curpoint))
  		;(draw-burst (mod (first (<! c)) 500) (- (mod (second (<! c)) 500) 90) (rand-int 20) (rand-int 100))
  		;(draw-point (mod (first (<! c)) 500) (mod (second (<! c)) 500))
        ;(.log js/console (<! c)))))
		)))


-0.9537617134939987 cljs.js:19185
0.12360303600011291 cljs.js:19185
0.8508876886558596 cljs.js:19185
-0.8317914757822045 cljs.js:19185
-0.15859290602857282 cljs.js:19185
0.9637873480674221 cljs.js:19185
-0.6435612059762619 cljs.js:19185
-0.42815542808445156 cljs.js:19185
0.9999122598719259 cljs.js:19185
-0.4040652194563607 cljs.js:19185
-0.6636113342009432 cljs.js:19185
0.9563847343054627 cljs.js:19185
-0.13238162920545193 cljs.js:19185
-0.8462043418838514 cljs.js:19185
0.8366721491002946 cljs.js:19185
0.14984740573347818 cljs.js:19185
-0.9613891968218607 cljs.js:19185
0.6503107401625525 cljs.js:19185
0.4201396822393068 cljs.js:19185
-0.9999903395061709 cljs.js:19185
0.412145950487085 cljs.js:19185
0.6569638725243397 cljs.js:19185
-0.9589328250406132 cljs.js:19185
0.14114985067939137 cljs.js:19185
0.8414546973619527 cljs.js:19185
-0.8414872714892108 cljs.js:19185
-0.14109016531210986 cljs.js:19185
0.9589157234143065 cljs.js:19185
-0.6570093243162466 cljs.js:19185
-0.41209101962194344 cljs.js:19185
0.9999900726865629 cljs.js:19185
-0.42019439103217676 cljs.js:19185
-0.6502649395607762 cljs.js:19185
0.9614057860636483 cljs.js:19185
-0.14990701345623575 cljs.js:19185
-0.8366391272115635 cljs.js:19185
0.8462364656975445 cljs.js:19185
0.13232187086982847 cljs.js:19185
-0.9563671216345017 cljs.js:19185
0.6636564336219596 cljs.js:19185
0.4040100708226276 cljs.js:19185
-0.9999114594340064 cljs.js:19185
0.42820991051876856 cljs.js:19185
0.6435150601529656 cljs.js:19185
-0.9638034236249698 cljs.js:19185
0.15865243143670757 cljs.js:19185
0.8317580087191733 cljs.js:19185