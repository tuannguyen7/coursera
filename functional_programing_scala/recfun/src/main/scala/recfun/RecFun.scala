package recfun

import scala.annotation.tailrec

object RecFun extends RecFunInterface {

  def main(args: Array[String]): Unit = {
    println("Pascal's Triangle")
    for (row <- 0 to 10) {
      for (col <- 0 to row)
        print(s"${pascal(col, row)} ")
      println()
    }
  }

  /**
   * Exercise 1
   */
  def pascal(c: Int, r: Int): Int = {
    @tailrec
    def loop(c: Int, r: Int, v: Int): Int = {
      if (c == 0 || c == r || r == 0)
        1 + v
      else
        loop(c - 1, r - 1, v + pascal(c, r - 1))
    }
    loop(c, r, 0)
  }

  /**
   * Exercise 2
   */
  def balance(chars: List[Char]): Boolean = {
    @tailrec
    def check(chars: List[Char], count: Int): Boolean = {
      if (chars.length == 0) {
        count == 0
      } else if (chars.head == '(') {
        check(chars.tail, count + 1)
      } else if (chars.head == ')' ) {
        if (count <= 0)
          false
        else
          check(chars.tail, count - 1)
      } else {
        check(chars.tail, count)
      }
    }
    check(chars, 0)
  }

  /**
   * Exercise 3
   */
  def countChange(money: Int, coins: List[Int]): Int = {
    if (money == 0)
      1
    else if (money < 0)
      0
    else if (coins.isEmpty)
      0
    else
      countChange(money - coins.head, coins) + countChange(money, coins.tail)
  }
}

