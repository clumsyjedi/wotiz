(ns wotiz.core
  (:require [clojure.string]))

(defn wotiz
  ([arg] (wotiz arg "" 0))
  ([arg msg depth]
   (let [indent (clojure.string/join (for [n (range depth)] " "))]
     (println (type arg) arg)
     (str msg (cond (and (map? arg) (not (seq? arg)))
                    (let [amap (array-map (first (keys arg)) (first (vals arg)))]
                      (wotiz (first amap)
                              (str indent "A map with a first entry of\n")
                              (inc depth)))

                    (map? arg)
                    (wotiz (first arg)
                            (str indent "A map with a first entry of\n")
                            (inc depth))

                    (= clojure.lang.MapEntry (type arg))
                    (str (wotiz (key arg)
                                 (str indent "A MapEntry with key of\n")
                                 (inc depth))
                         (wotiz (val arg)
                                 (str indent "and a value of\n")
                                 (inc depth)))

                    (and (seq? arg) (not-empty arg))
                    (wotiz (first arg)
                            (str indent "A seq containing with the first element of\n")
                            (inc depth))

                    (and (seq? arg) (empty? arg))
                    (wotiz (first arg)
                            (str indent "A seq containing with the first element of\n")
                            (inc depth))

                    (nil? arg)
                    (str indent "nil\n")

                    :else (str indent (type arg) "\n"))))))
