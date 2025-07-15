package quickcheck

import org.scalacheck.*
import Arbitrary.*
import Gen.*
import Prop.forAll

abstract class QuickCheckHeap extends Properties("Heap") with IntHeap:
  lazy val genHeap: Gen[H] = oneOf(
    const(empty),
    for {
      number <- arbitrary[Int]
      heap <- genHeap
    } yield insert(number, heap)
  )

  given Arbitrary[H] = Arbitrary(genHeap)

  property("gen1") = forAll { (h: H) =>
    val m = if isEmpty(h) then 0 else findMin(h)
    findMin(insert(m, h)) == m
  }

  property("min1") = forAll { (a: Int, h: H) =>
    val h = insert(a, empty)
    findMin(h) == a
  }

  property("insert many elements") = forAll { (arr: List[Int], h: H) =>
    if (arr.nonEmpty) {
      val h = arr.foldLeft(empty)((h, element) => insert(element, h))
      findMin(h) == arr.min
    } else {
      true
    }
  }

  property("insert then delete work") = forAll { (arr: List[Int], h: H) =>
    if (arr.nonEmpty) {
      val h = arr.foldLeft(empty)((h, element) => insert(element, h))
      val sortedArr = arr.sorted
      val rs = sortedArr.foldLeft((h, true))((rs, element) => {
        val newH = rs._1
        val lastRsSuccess = rs._2
        if (lastRsSuccess) {
          (deleteMin(newH), findMin(newH) == element)
        } else {
          rs
        }
      })
      rs._2
    } else {
      true
    }
  }

  property("merge two item") = forAll {(a1: Int, a2: Int, h1: H, h2: H) =>
    val newH1 = insert(a1, h1)
    val newH2 = insert(a2, h2)
    val minH1 = findMin(newH1)
    val minH2 = findMin(newH2)
    val h = meld(newH1, newH2)
    findMin(h) == Math.min(minH1, minH2)
  }

  property("delete min") = forAll { (a: Int, h1: H) =>
    val newH = insert(a, h1)
    val m = findMin(newH)
    val h2 = deleteMin(newH)
    if (isEmpty(h2)) {
      true
    } else {
      findMin(h2) >= m
    }
  }

  property("priority") = forAll { (h: H) =>
    def travel(h: H, lastMin: Int): Boolean = {
      if (isEmpty(h))
        true
      else {
        val curMin = findMin(h)
        if (curMin < lastMin)
          false
        else
          travel(deleteMin(h), curMin)
      }
    }

    travel(h, Integer.MIN_VALUE)
  }