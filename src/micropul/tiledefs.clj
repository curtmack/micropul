(ns ^{
  :doc
  "
  Contains the definitions of every tile in the game.
  "
  :author "Curtis Mackie" }

  micropul.tiledefs
  (:require [micropul.tile])
  (:import [micropul.tile NormalTile BigTile]))

(def tiledefs
  (concat
    (for [objects [
            ; starting tile
            [:w :w
             :b :b]

            ; row 1
            [:w :0
             :0 :0]
            [:b :0
             :0 :0]

            [:w :b
             :p :0]
            [:b :w
             :p :0]

            [:p :w
             :w :b]
            [:p :b
             :b :w]

            ; row 2
            [:w :0
             :0 :d]
            [:b :0
             :0 :d]

            [:w :b
             :0 :d]
            [:b :w
             :0 :d]

            [:d :w
             :w :b]
            [:d :b
             :b :w]

            ; row 3
            [:w :0
             :d :0]
            [:b :0
             :d :0]

            [:w :w
             :0 :d]
            [:b :b
             :0 :d]

            [:w :w
             :d :w]
            [:b :b
             :d :b]

            ; row 4
            [:w :d
             :p :0]
            [:b :d
             :p :0]

            [:w :w
             :D :0]
            [:b :b
             :D :0]

            [:w :w
             :D :b]
            [:b :b
             :D :w]

            ; row 5
            [:w :d
             :0 :d]
            [:b :d
             :0 :d]

            [:w :0
             :0 :w]
            [:b :0
             :0 :b]

            [:w :w
             :w :b]
            [:b :b
             :b :w]

            ; row 6
            [:w :D
             :0 :d]
            [:b :D
             :0 :d]

            [:w :0
             :d :b]
            [:b :0
             :d :w]

            [:w :b
             :b :w]
            [:b :w
             :w :b]

            ; row 7
            ; big tiles omitted

            [:w :p
             :d :w]
            [:b :p
             :d :b]

            ; starting tile omitted
            [:b :b
             :w :w]

            ; row 8
            ; big tiles omitted
            
            [:w :d
             :d :b]
            [:b :d
             :d :w]

            [:w :w
             :w :w]
            [:b :b
             :b :b] ] ]
      ; for objects
      (NormalTile. objects))
    ; concat with
    [
      (BigTile. :w :p)
      (BigTile. :b :p)
      (BigTile. :w :d)
      (BigTile. :b :d)
    ]))
