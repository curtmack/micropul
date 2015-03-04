(ns ^{
  :doc
  "
  Contains the protocols and records for managing puls, catalysts, and their
  bounding boxes within the game grid.
  "
  :author "Curtis Mackie" }

  micropul.tileobj)

(defprotocol Catalyst
  "Provides the operations of a catalyst - when activated, it performs
  an action."
  (activate [this game-state]
    "Performs the catalyst action, returns resulting state."))

(defprotocol BindPoint
  "Provides the operations of a pul - they bind tiles, bind to a group, and
   have a score."
  (color [this] "The binding color of the pul.")
  (score [this] "The score value of the pul (always 1 except in solitaire)."))

(defprotocol Bounded
  "Provides the operations associated with taking up space - it has a position,
   a width, and a height."
  (pos [this] "The position of the object, as map with :x and :y.")
  (width [this] "The width of the object (along the west-east axis).")
  (height [this] "The height of the object (along the north-south axis)."))

(defprotocol HasArt
  "Provides operations for objects to provide a description of their artwork."
  (art-desc [this]
  "A map of `:filename` (the filename to use) and `:rotate` (the integer
   number of times to rotate the art 90 degrees clockwise)."))

(defn north [obj]
  "Finds the north edge coordinate of a Bounded object."
  (-> obj (pos) (:y)))

(defn south [obj]
  "Finds the south edge coordinate of a Bounded object."
  (-> obj (pos) (:y) (+ (height obj)) (dec)))

(defn west [obj]
  "Finds the west edge coordinate of a Bounded object."
  (-> obj (pos) (:x)))

(defn east [obj]
  "Finds the east edge coordinate of a Bounded object."
  (-> obj (pos) (:x) (+ (width obj)) (dec)))

(defn interior [obj]
  "Finds the set of interior points contained within a Bounded object."
  (set
    (for [i (range (width obj))
          j (range (height obj))]
      { :x (-> obj (pos) (:x) (+ i))
        :y (-> obj (pos) (:y) (+ j)) })))

(defn neighbors [obj]
  "Finds the set of points that neighbor a Bounded object."
  (set
    (concat
      ; north neighbors
      (for [i (range (width obj))]
        { :x (-> obj (pos) (:x) (+ i))
          :y (dec (north obj)) })
      ; south neighbors
      (for [i (range (width obj))]
        { :x (-> obj (pos) (:x) (+ i))
          :y (inc (south obj)) })
      ; west neighbors
      (for [j (range (height obj))]
        { :x (dec (west obj))
          :y (-> obj (pos) (:y) (+ j)) })
      ; east neighbors
      (for [j (range (width obj))]
        { :x (inc (east obj))
          :y (-> obj (pos) (:y) (+ j)) }))))

(defrecord DotCatalyst [dots rotate pos width height]
  Catalyst
    (activate [this game-state]
      ; TODO - stocks tiles based on number of dots
      )
  Bounded
    (pos [this]
      { :x (:x this)
        :y (:y this) })
    (width [this] (:width this))
    (height [this] (:height this))

  HasArt
    (art-desc [this]
      (let [filename (str "dot-" (:dots this) "-corner.png")
            rotate (:rotate this)]
        { :filename filename
          :rotate rotate })))

(defrecord PlusCatalyst [pos rotate width height]
  Catalyst
    (activate [this game-state]
      ; TODO - extra turn for current player
      )
  Bounded
    (pos [this]
      { :x (:x this)
        :y (:y this) })
    (width [this] (:width this))
    (height [this] (:height this))
  HasArt
    (art-desc [this]
      { :filename "plus-corner.png"
        :rotate (:rotate this) }))

(defrecord Pul [color score rotate width height]
  BindPoint
    (color [this] (:color this))
    (score [this] (:score this))
  Bounded
    (pos [this]
      { :x (:x this)
        :y (:y this) })
    (width [this] (:width this))
    (height [this] (:height this))
  HasArt
    (art-desc [this]
      { :filename (str "pul-" (color this) "-corner.png"
        :rotate (:rotate this) }))
