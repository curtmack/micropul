(ns micropul.test.tileobj
  (:use [clojure.test]
        [micropul.tileobj])
  (:import [micropul.tileobj Pul]))

(deftest bounded-tests
  (let [bounded-1 (Pul. "b" 1 {:x 0 :y 0} 0 1 1)
        bounded-2 (Pul. "b" 1 {:x 0 :y 0} 0 2 2)]
    (is (= 0 (north bounded-1)) "north returns wrong coordinate for 1x1 object")
    (is (= 0 (south bounded-1)) "south returns wrong coordinate for 1x1 object")
    (is (= 0 (west bounded-1)) "west returns wrong coordinate for 1x1 object")
    (is (= 0 (east bounded-1)) "east returns wrong coordinate for 1x1 object")
    (is (= #{ {:x 0 :y 0} }
      (interior bounded-1)) "interior returns wrong coordinates for 1x1 object")
    (is (= #{ {:x 0 :y 1} {:x 0 :y -1}
              {:x 1 :y 0} {:x -1 :y 0} }
      (neighbors bounded-1)) "neighbors returns wrong coordinates for 1x1 object")

    (is (= 0 (north bounded-2)) "north returns wrong coordinate for 2x2 object")
    (is (= 1 (south bounded-2)) "south returns wrong coordinate for 2x2 object")
    (is (= 0 (west bounded-2)) "west returns wrong coordinate for 2x2 object")
    (is (= 1 (east bounded-2)) "east returns wrong coordinate for 2x2 object")
    (is (= #{ {:x 0 :y 0} {:x 1 :y 0}
              {:x 0 :y 1} {:x 1 :y 1} }
      (interior bounded-2)) "interior returns wrong coordinates for 2x2 object")
    (is (= #{ {:x 0 :y -1} {:x 1 :y -1}
              {:x 0 :y 2} {:x 1 :y 2}
              {:x -1 :y 0 } {:x -1 :y 1}
              {:x 2 :y 0} {:x 2 :y 1} }
      (neighbors bounded-2)) "neighbors returns wrong coordinates for 2x2 object")
    ))
