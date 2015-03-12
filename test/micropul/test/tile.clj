(ns micropul.test.tile
  (:use [clojure.test]
        [micropul tile tileobj])
  (:import [micropul.tile NormalTile BigTile]
           [micropul.tileobj Pul DotCatalyst PlusCatalyst]))

(deftest permute-vector-tests
  (is (= [0 1 2 3] (permute-vector [0 1 2 3] [0 1 2 3]))
    "permute-vector does not permute identity correctly")
  (is (= [1 0 2 3] (permute-vector [0 1 2 3] [1 0 2 3]))
    "permute-vector does not permute swap correctly")
  (is (= [\c \b \d \a] (permute-vector [\a \b \c \d] [2 1 3 0]))
    "permute-vector does not permute a case with letters correctly"))
(deftest rot-permutations-tests
  (let [start-tile [\a \b
                    \c \d]]
    (is
      (=
        [\a \b
         \c \d]
        (permute-vector start-tile (get rot-permutations 0)))
      "rot-permutation for rot 0 is incorrect")
    (is
      (=
        [\c \a
         \d \b]
        (permute-vector start-tile (get rot-permutations 1)))
      "rot-permutation for rot 1 is incorrect")
    (is
      (=
        [\d \c
         \b \a]
        (permute-vector start-tile (get rot-permutations 2)))
      "rot-permutation for rot 2 is incorrect")
    (is
      (=
        [\b \d
         \a \c]
        (permute-vector start-tile (get rot-permutations 3)))
      "rot-permutation for rot 3 is incorrect")))

(deftest NormalTile-tests
  (let [testtile (NormalTile. [:w :D
                               :0 :d])
        testplus (NormalTile. [:w :b
                               :p :0])]
    (is (= (place testtile {:x 0 :y 0} 1)
          #{ (Pul. :w 1 {:x 1 :y 0} 1 1 1)
             (DotCatalyst. 2 {:x 1 :y 1} 1 1 1)
             (DotCatalyst. 1 {:x 0 :y 1} 1 1 1) })
      "Test NormalTile not placed correctly when rotated once")
    (is (= (place testtile {:x 2 :y 2} 0)
          #{ (Pul. :w 1 {:x 2 :y 2} 0 1 1)
             (DotCatalyst. 2 {:x 3 :y 2} 0 1 1)
             (DotCatalyst. 1 {:x 3 :y 3} 0 1 1) })
      "Test NormalTile not placed off-origin correctly")
    (is (= (place testplus {:x 0 :y 0} 0)
          #{ (Pul. :w 1 {:x 0 :y 0} 0 1 1)
             (Pul. :b 1 {:x 1 :y 0} 0 1 1)
             (PlusCatalyst. {:x 0 :y 1} 0 1 1) })
      "Test NormalTile with plus not placed correctly")))

(deftest BigTile-tests
  (let [testtile (BigTile. :w :d)
        testplus (BigTile. :b :p)]
    (is (= (place testtile {:x 0 :y 0} 1)
          #{ (Pul. :w 1 {:x 0 :y 0} 1 2 2)
             (DotCatalyst. 1 {:x 0 :y 0} 1 2 2) })
      "Test BigTile not placed correctly when rotated once")
    (is (= (place testtile {:x 2 :y 2} 0)
          #{ (Pul. :w 1 {:x 2 :y 2} 0 2 2)
             (DotCatalyst. 1 {:x 2 :y 2} 0 2 2) })
      "Test BigTile not placed correctly off-origin")
    (is (= (place testplus {:x 0 :y 0} 0)
          #{ (Pul. :b 1 {:x 0 :y 0} 0 2 2)
             (PlusCatalyst. {:x 0 :y 0} 0 2 2) })
      "Test BigTile with plus not placed correctly")))
