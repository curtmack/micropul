(ns ^{
  :doc
  "
  Contains the protocols and records for managing tiles and their relation to each other
  "
  :author "Curtis Mackie" }

  micropul.tile
  (:use [micropul.tileobj
          :only [north south west east interior neighbors]])
  (:import [micropul.tileobj Pul DotCatalyst PlusCatalyst]))

(defn permute-vector
  "Permutes a vector of size n based on a permutation list containing each number 0..(n-1)"
  [v permutation]
  (vec
    (for [i permutation]
      (get v i))))

(def rot-permutations
  [
    ; rotate 0 times, i.e. no rotation
    [0 1
     2 3]
    ; rotate 1 time, 90 degrees
    [2 0
     3 1]
    ; rotate 2 times, 180 degrees
    [3 2
     1 0]
    ; rotate 3 times, 270 degrees
    [1 3
     0 2]
  ])

(defprotocol TileDef
  "Provides the operations of a tile definition - creating the tile objects based on placement"
  (place [this pos rot]
    "Returns a set of tile objects that will be created from this placement."))

(defrecord NormalTile [objects]
  TileDef
    (place [this pos rot]
      (let [rot-objects (permute-vector (:objects this) (get rot-permutations rot))]
        (set
          (filter (complement nil?)
            (for [i (range 4)
                  :let [obj (get rot-objects i)
                        x (-> i (mod 2) (+ (:x pos)))
                        y (-> i (quot 2) (+ (:y pos)))
                        obj-pos {:x x :y y}]]
              (case obj
                :b (Pul. :b 1 obj-pos rot 1 1)
                :w (Pul. :w 1 obj-pos rot 1 1)
                :d (DotCatalyst. 1 obj-pos rot 1 1)
                :D (DotCatalyst. 2 obj-pos rot 1 1)
                :p (PlusCatalyst. obj-pos rot 1 1)
                :0 nil)))))))


(defrecord BigTile [color catalyst]
  TileDef
    (place [this pos rot]
      #{
        ; TODO - score of big Pul needs to be 5 in solitaire mode
        (Pul. (:color this) 1 pos rot 2 2)
        (case (:catalyst this)
          :d (DotCatalyst. 1 pos rot 2 2)
          :p (PlusCatalyst. pos rot 2 2))
      }))
