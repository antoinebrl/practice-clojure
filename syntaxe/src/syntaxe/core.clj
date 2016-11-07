(ns syntaxe.core
  (:gen-class))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!")
  (println args)
  (println (str "Hello, World!"))
  (println (+ 1 2))
  (do
    (print "Hello")
    "World")
  )

; Inspire from :
; https://learnxinyminutes.com/docs/clojure/
; https://kimh.github.io/clojure-by-example/#-%3E

; (....) is called a form
; the association name-value is called binding and not assignment

;;;;;;;;;;;;;;;;;;;
; Maths
;;;;;;;;;;;;;;;;;;;
; https://en.wikipedia.org/wiki/Polish_notation
(+ 1 1) ; => 2
(+ 1. 1) ; => 2.
(+ 1 1.) ; => 2.
(+ 1 2 3 4 5 6 7 8 9) ; => 45
(- 2 1) ; => 1
(- 45 1 2 3 4 5 6 7 8 9) ; => 0
(* 2 2) ; => 4
(* 2 2 2) ; => 8
(/ 10 5) ; => 2
(/ 10 3) ; => 10/3 native fraction type
(- 1 (/ (+ 1 9) 10)) ; => 0
(mod 11 2) ; => 1
(mod -11 2) ; => 1
(mod 11.3 2) ; => 1.3000000000000007
(max 1 2 3 4 5 ) ; => 5
(min 1 2 3 4 5 ) ; => 1

(inc 10) ; => 11
(dec 10) ; => 9

; Arbitrary precision
(apply + (range 10000000000000 10000000001000)) ; => integer overflow
(apply +' (range 10000000000000 10000000001000)) ; => 1000000000499500

(- 0 9000000000000000000 1000000000000000000) ; => integer overflow
(-' 0 9000000000000000000 1000000000000000000) ; => 10000000000000000000N

(* 1234567890 9876543210) ; => integer overflow
(*' 1234567890 9876543210) ; => 12193263111263526900N



;;;;;;;;;;;;;;;;;;;
; Boolen
;;;;;;;;;;;;;;;;;;;
(not true) ; => true
(not false) ; => false
(= true false) ; => false
(= 2 2) ; => true
(= 1 2) ; => false
(< 1 2) ; => true
(<= 2 2) ; => true
(> 1 2) ; => false
(>= 2 2) ; => true

;;;;;;;;;;;;;;;;;;;
; Types
;;;;;;;;;;;;;;;;;;;
; class use native java classes
(= 1 1.) ; => false
(= 1 true) ; => false
(= 1 (class 1)) ; => false
(= 1. (class 1.)) ; => false
(= "" (class "")) ; => false
(= false (class true)) ; => false
(= nil (class nil)) ; => true
(boolean true) ; => true
(boolean false) ; => false
(boolean 0) ; => true ; BEWARE !
(boolean 1) ; => true
(boolean 2) ; => true
(boolean "hello") ; => true
(boolean nil) ; => false
; everything except false and nil are true

;;;;;;;;;;;;;;;;;;;
; List and vectors
;;;;;;;;;;;;;;;;;;;
; Everything is a list. Use ' if evaluation is not wanted
(+ 1 2 3) ; => 6
'(+ 1 2 3) ; => (+ 1 2 3)
(quote (+ 1 2 3)) ; => (+ 1 2 3)
; Evaluate a list
(eval '(+ 2 3 4)) ; => 9

; Lists are linked-list (clojure.lang.PersistentList)
; Vectors are array-backed (clojure.lang.PersistentVector)
(class '(1 2 3 4)) ; => clojure.lang.PersistentList
(class [1 2 3 4]); => clojure.lang.PersistentVector

; Collection = group of data
(coll? '(1 2 3 4)) ; => true
(coll? [1 2 3 4]) ; => true
; Sequence = list
(seq? '(1 2 3 4)) ; => true
(seq? [1 2 3 4]) ; => false
; Conversion to list
(seq [1 2 3 4]) ; => (1 2 3 4)
(seq #{1 2 3 4}) ; => (1 2 3 4)

; add an item at the begining
(cons 0 [1 2 3 4]) ; => (0 1 2 3 4)
(cons 0 (range 1 5)) ; => (0 1 2 3 4)
; add an item
(conj [1 2 3 4] 5) ; => [1 2 3 4 5] ; at the end
(conj (range 1 5) 0) ; => (0 1 2 3 4) ; at the begining
(conj (range 1 5) 0 -1 -2) ; => (-2 -1 0 1 2 3 4)

(filter odd? (range 1 5)) ; => (1 3)
(filter even? (range 1 5)) ; => (2 4)

(reduce + (range 1 10)) ; => 45
(reduce * (range 1 10)) ; => 362880 ; = 9!

; select en element. we start counting at 0 of course
(nth '(1 2 3 4 5) 3) ; => 4
(nth [1 2 3] 1) ; => 2
(.indexOf ["a" "b" "c"] "a") ; => 0 (java method)
(.indexOf ["a" "b" "c"] "z") ; => -1 (java method)

; reverse a sequence
(reverse [1 2 3]) ; => (3 2 1)

;;;;;;;;;;;;;;;;;;;;;
; Functions
;;;;;;;;;;;;;;;;;;;;;
(fn [] "Hello World !") ; => fn ; anonymous function
((fn [] "Hello World !")) ; => "Hello World !" ; call of an anomynous function
((fn [x] (and (> x 0) (< x 10))) 0) ; => false
((fn [x] (and (> x 0) (< x 10))) 5) ; => true

; Constant
(def x 5)
x ; => 5

; Named functions
(def Hello (fn [] "Hello World !"))
(Hello) ; => "Hello Word !"

(defn hello "this is a doc" [] "Hello World !") ; defn = def + fn
(hello) ; => "Hello Word !"
(doc hello) ; => this is a doc
(meta #'hello)

; arguments
(defn hello-name [name] (str "Hello " name "!"))
(hello-name "Bob") ; => "Hello Bob!"

; syntaxe
(def hello-name2 #(str "Hello " %1 ))
(hello-name2 "Alice") ; => "Hello Alice"

; packed arguments
(defn count-args [& args] (str (count args)))
(count-args "test test" 1 2 3 false) ; => "5"

; named and packed arguments mixed
(defn hello-count [name & args] (str name ", nb args :" (count args)))
(hello-count "John" "test" 2 3 4 true) ; => "John, nb args :5"

; multi-variadic functions
(defn hello-you
  ([] "Hello Word !")
  ([name] (str "Hello " name " !")))
(hello-you) ; => "Hello Word !"
(hello-you "Smith") ; => "Hello Smith !"

; arg manipulation
; % replace all args, %1 the first one, ...
(let [incr #(+ 1 %)]
  (incr 10)) ; => 11

; Closure
(defn say [str] (fn [] (print str) )) ; say is a closure
(def wouf (say "wouf"))
(def miaou (say "miaou"))
(wouf) ; => wouf
(miaou) ; => miaou


;;;;;;;;;;;;;;;;;;;;;
; Maps
;;;;;;;;;;;;;;;;;;;;;
(class {:a 1 :b 2 :c 3}) ; => clojure.lang.PersistentArrayMap
(class (hash-map :a 1 :b 2 :c 3)) ; => clojure.lang.PersistentHashMap

(def strmap {"a" 1 "b" 2 "c" 3})
strmap ; => {"a" 1, "b" 2, "c" 3} ; no parenthesis
(strmap "a") ; => 1 ; access by calling it as a fct
(strmap "zzz") ; => nil ; "zzz" is not a key

(def keymap {:a 1 :b 2 :c 3})
keymap ; => {:a 1, :b 2, :c 3} ; no parenthesis
(keymap :a) ; => 1 ; access by calling it as a fct
(:a keymap) ; => a ; keyword retrives value
(keymap :zzz) ; => nil ; :zzz is not a key
(keymap "a")  ; => nil

({:abc "hello" :xyz "bye"} :abc) ; => "hello

; add new key
(assoc keymap :d 4) ; => {:a 1, :b 2, :c 3, :d 4}
(assoc strmap "d" 4) ;=> {"a" 1, "b" 2, "c" 3, "d" 4}
; remove key
(dissoc keymap :a) ; => {:b 2, :c 3}
(dissoc keymap :zzz) ; => {:a 1, :b 2, :c 3}
(dissoc strmap "a") ; => {"b" 2, "c" 3}
(dissoc keymap :a :b :c) ; => {}

; merge maps
(merge {"orange", "color" "pen" "object"} {1 2} {"a" "A" "z" "Z"})
; => {"orange" "color", "pen" "object", 1 2, "a" "A", "z" "Z"}
; get keys
(keys {"orange", "color" "pen" "object"}) ; => ("orange" "pen")
; get values
(vals {"orange", "color" "pen" "object"}) ; => ("color" "object")

;;;;;;;;;;;;;;;;;;;;;
; Sets
;;;;;;;;;;;;;;;;;;;;;
(class #{1 2 3}) ; => clojure.lang.PersistentHashSet
(set [1 2 3 1 2 3 3 2 1]) ; => #{1 2 3}

; Add an item
(conj #{1 2 3} 4) ; => #{1 2 3 4}
(conj #{1 2 3} 1) ; => #{1 2 3}

; Remove an item
(disj #{1 2 3} 1) ; => #{2 3}

; existence
(#{"a" "b" "c" "d" "e" "f" "g" "h"} "a") ; => "a"
(#{"a" "b" "c" "d" "e" "f" "g" "h"} 1) ; => nil

(contains? #{1 2 3 4 5} 2) ; => true
(clojure.set/subset? #{1 2} #{1 2 3 4}) ; => true
(clojure.set/superset? #{1 2 3} #{1 2}) ; => true

;;;;;;;;;;;;;;;;;;;;;
; Collections
;;;;;;;;;;;;;;;;;;;;;

; data abstraction with map
(map inc `(1 2 3)) ; => (2 3 4)
(map inc [1 2 3]) ; => (2 3 4)
(map inc #{1 2 3}) ; => (2 3 4)
(map (fn [x] (+ x 1)) '(1 2 3)) ; => (2 3 4) ; we can have our own fct

; reduce set to values and apply an operation
(reduce + '(1 2 3)) ; => 6
(reduce + [1 2 3]) ; => 6
(reduce + #{1 2 3}) ; => 6
(reduce + (range 10)) ; => 45
(reduce (fn [x,y] (* x (+ y 1))) [1 2 3]) ; => 12; left-right evaluation

(defn pow [x n]
  (reduce * (repeat n x)))
(pow 2 6) ; => 64

; conversions
(into [] `(1 2 3)) ; => [1 2 3]
(into (list) [1 2 3]) ; => (3 2 1)
(into #{} [1 2 3]) ; => #{1 2 3}
(reduce conj #{} [1 2 3]) ; => #{1 2 3}
(into {} [[:a 1] [:b 2] [:c 3]]) ; => {:a 1, :b 2, :c 3}
(into [] {:a 1 :b 2 :c 3}) ; => [[:c 3] [:b 2] [:a 1]]

(repeatedly 3 (fn [] (println "Hello!")) )

(doseq [i (range 1 10)]
  (print "i : ")
  (println i))

(doseq [i (range 1 10)]
  (doseq [j (range 1 10)]
    (print (if (= i j) " " "*")))
  (println))

(doseq [n1 [1 2 3] n2 [1 2 3]] (println (+ n1 n2)))


;;;;;;;;;;;;;;;;;;;
; Range and number
;;;;;;;;;;;;;;;;;;;
(range 10) ; => (0 1 2 3 4 5 6 7 8 9)
(count (range 10)) ; => 10
(range 0 0.8 1/10) ; => (0 1/10 1/5 3/10 2/5 1/2 3/5 7/10)
(range 0 0.8 0.1) ; => (0 0.1 0.2 0.30000000000000004 0.4 0.5 0.6 0.7 0.7999999999999999)
(range 10 -10 -1) ; => (10 9 8 7 6 5 4 3 2 1 0 -1 -2 -3 -4 -5 -6 -7 -8 -9)
(take 10 (range)) ; => (0 1 2 3 4 5 6 7 8 9)
(take 10 (drop 40 (range))) ; => (40 41 42 43 44 45 46 47 48 49)
(range 0. 1. 0.1) ; => (0.0 0.1 0.2 0.30000000000000004 0.4 0.5 0.6 0.7 0.7999999999999999 0.8999999999999999 0.9999999999999999)


;;;;;;;;;;;;;;;;;;;;;
; Statements
;;;;;;;;;;;;;;;;;;;;;
; (if cond actionIfTrue actionIfFalse)
(if true "it's true" "it's false") ; => "it's true"
(if false "test") ; => nil

; do groups statemens
(do
  (print "Hello")
  "World")

(if true
  (do
    (println "first instrucion in if")
    (println "second instruction in if"))) ; => first instrucion in if
                                            ; second instruction in if
; if we care only on the true evaluation, this is equivalent to :
(when true
  (println "first instrucion in if")
  (println "second instruction in if")) ; => first instrucion in if
                                          ; second instruction in if

(if '() "empty" "not empty") ; => "empty"
(if {} "empty" "not empty") ; => "empty"
(if [] "empty" "not empty") ; => "empty"
(if #{} "empty" "not empty") ; => "empty"

; Case - Switch
(defn case-test [n]
  (case n
    1 "one"
    2 "two"
    "other"))
(case-test 2) ; => two

; Cond : a switch with personal condition
(defn cond-test [n]
  (cond
    (= (mod n 2) 1) "impair"
    (= (mod n 2) 0) "pair"
    :else "not an integer")) ; not the presence of the keyword :else
(cond-test 2) ; => pair
(cond-test 1) ; => impair
(cond-test 4.4) ; => not an integer

; let creates temporary bindings
(let [enemy1 "john" dist1 3 enemy2 "will" dist2 2]
  (print "enemy")
  (if (> dist1 dist2) enemy2 enemy1)) ; =>   enemy=> "will"

; scope and override (no override)
(let [a "a"]
  (let [a "A"]
    (let [a "AA"]
      (print a))
    (print a))
  (print a)) ; AAAa

(defn print-and-say-hello [name]
  (print "Saying hello to " name)
  (str "Hello " name))
(print-and-say-hello "Jeff") ; => Saying hello to  Jeff=> "Hello Jeff"

(let [name "Urkel"]
  (print "Saying hello to " name)
  (str "Hello " name)) ; => Saying hello to  Urkel=> "Hello Urkel


;;;;;;;;;;;;;;;;;;;;;
; Threading macros
;;;;;;;;;;;;;;;;;;;;;
; -> passing down the evaluation of former forms to the first argument of preceding forms
(->
  {:a 1 :b 2}
  (assoc :c 3)
  (dissoc :b)
  (:a)) ; => 1

; ->> same thing, but inserts the result of each line at the *end* of the form.
(->>
  (range 10)
  (map inc)
  (filter even?)
  (into [])) ; => [2 4 6 8 10]

; as-> puts last form where you want
(as-> [1 2 3] input
    (map inc input)
    (conj input 1)) ; => (1 2 3 4)




