(ns assign2prop.core)
(import java.io.FileReader java.io.File)
(import java.io.Closeable)

;If an exception is thrown inside the expression the exception should be returned.
;Otherwise the evaluated value of the expression should be returned. If a binding is
;provided it should be possible to use the bound variable inside the expression.
;
;
; After
;execution of the form any variables that are bound shall be closed (e.g.
; (let [s (Socket.)] (. s close))) Note that closeable classes in Java implements
;the Closeable–interface (hint: use type hints). The return value of the macro
;is either the return value of the executed form or an exception.
;(defmacro safe [vect expr]
;  ; ta index 0 i vect och sätt till index 1 i vect (kombinera)
;
;  `(let [~(symbol (first vect)) (FileReader. (File. "C:\\Users\\dogge\\IdeaProjects\\assign2prop\\src\\assign2prop\\text.txt"))])
;
;  )

(defmacro safe [vect & expr]

  ; utvärdera expression och kasta exception ifall det finns ett exception

  ;`(try (let ~vect) (catch Exception e# e#))

  `(let ~vect (try ~@expr (catch Exception e# e#))          ; här binds värdet s till vektorn

              )

  )
;;UPPGIFT 1
(defmacro safe [& parameters]
  (if (> (count parameters) 1)

    `(try
       (let ~(first parameters) (def expression ~(second parameters)) ; define expr for later use
                                (if (instance? Closeable ~(ffirst parameters))
                                  (.close ~(ffirst parameters))) expression) ;return expression after close first value in vector
       (catch Exception e# (str "Exception caught: " e#)))

    `(try
       ~@parameters
       (catch Exception e# (str "Exception caught: " e#)))))



; UPPGIFT 2
(def <> "notEqual")
;;from
(defmacro from [table]
  `(if (set? ~table)
     (do ~table)
     )
  )

;;extractRowValues
(defn extractRowValues [row selKeys]
  (let [extRow {}]
    (if (> (count selKeys) 1)
      (conj
        (assoc extRow (first selKeys) (get row (first selKeys)))
        (conj extRow (extractRowValues row (rest selKeys)))
        )

      (assoc extRow (first selKeys) (get row (first selKeys)))
      )
    )
  )

;;extractColumns
(defn extractColumns [table selKeys]
  (if (> (count table) 1)
    (set
      (concat
        (conj #{} (extractRowValues (first table) selKeys))
        (extractColumns (rest table) selKeys)
        )
      )

    (set (conj #{} (extractRowValues (first table) selKeys)))
    )
  )

;;WHERE

;;TODO WRITE WHERE CLAUSE



;;select
(defmacro select ([columns from table]
                  `(let [tbl# (~from ~table)]
                     (extractColumns tbl# ~columns)
                     )
                  )


  ([columns from table where col opList]
   `(let [tbl# (~from ~table)]
      (do
        (where ~col ~opList (extractColumns tbl# ~columns))
        )
      )
   )

  )




(defn -main []
  ;;TODO WHERE
  (def squad #{
                 {:id 1 :name "Dogge"}
                 {:id 2 :name "Peter"}
                 {:id 3 :name "fabian"}
                 {:id 4 :name "Michel"}
                 })


  (println (select [:id :name] from squad))
  ;(def s (FileReader. (File. "C:\\Users\\dogge\\IdeaProjects\\assign2prop\\src\\assign2prop\\text.txt")))
  ;(def v (safe (/ 1 0)))
  ;(println v)
  ;(def v (safe [s (FileReader. (File. "C:\\Users\\dogge\\IdeaProjects\\assign2prop\\src\\assign2prop\\text.txt"))] (.read s)))
  ;(println v)
  )

