(ns micropul.test.tiledefs
  (:use [clojure.test]
        [micropul tiledefs tile])
  (:import [micropul.tile NormalTile BigTile]))

(deftest tiledefs-test
  (is (= (NormalTile. [:w :w :b :b]) (first tiledefs)) "Start tile doesn't look right")
  (is (every? (partial satisfies? TileDef) tiledefs) "Not every tiledef is a TileDef")
  (is (distinct? tiledefs) "Not all tiledefs are distinct"))
