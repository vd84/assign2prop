(ns assign2prop.core)
(import java.io.FileReader java.io.File)
(import java.io.Closeable)

;If an exception is thrown inside the expression the exception should be returned.
;Otherwise the evaluated value of the expression should be returned. If a binding is
;provided it should be possible to use the bound variable inside the expression. After
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

  (let [except `(catch Exception e# e#)]
  `(let ~vect (try ~@expr ~except)
              `(if (instance? java.io.Closeable `(first vect)) (.close `(first vect) ))


              )
   )

  )


(defn -main []
  ;(def s (FileReader. (File. "C:\\Users\\dogge\\IdeaProjects\\assign2prop\\src\\assign2prop\\text.txt")))
  ;(def v (safe [1 2] (/ 1 0)))
  (def v (safe [s (FileReader. (File. "C:\\Users\\dogge\\IdeaProjects\\assign2prop\\src\\assign2prop\\text.txt"))] (.read s)))
  (println v)
  )

