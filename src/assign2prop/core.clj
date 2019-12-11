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

(defmacro safe [expr]

  ; utvärdera expression och kasta exception ifall det finns ett exception

  `(try ~expr (catch Exception e# (str "Caught exception " e#)))

  ;`(let ~vect (try ~@expr (catch Exception e# e#))          ; här binds värdet s till vektorn

  )







(defn -main []
  ;(def s (FileReader. (File. "C:\\Users\\dogge\\IdeaProjects\\assign2prop\\src\\assign2prop\\text.txt")))
  (def v (safe (/ 1 0)))
  (def v (safe [s (FileReader. (File. "C:\\Usedsrs\\dogge\\IdeaProjects\\assign2prop\\src\\assign2prop\\text.txt"))] (.read s)))
  (println v)
  )

