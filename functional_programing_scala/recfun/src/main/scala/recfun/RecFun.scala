package recfun

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
   if (r == 0 && c == 0)
      1
   else if (r == 0 && c != 0)
      0
   else if (c == 0 || c == r )
      1
   else
     pascal(c, r - 1) + pascal(c - 1, r - 1)
  }

  /**
   * Exercise 2
   */
  def balance(chars: List[Char]): Boolean = {
    def balanceHelper(chars: List[Char], numParentheses: Int): Boolean = {
      if (chars.isEmpty)
        numParentheses == 0
      else if (chars.head == '(')
        balanceHelper(chars.tail, numParentheses + 1)
      else if (chars.head == ')') {
        if (numParentheses == 0)
          false
        else balanceHelper(chars.tail, numParentheses - 1)
      }
      else
        balanceHelper(chars.tail, numParentheses)
    }

    balanceHelper(chars, 0)
  }

  /**
   * Exercise 3
   */
  def countChange(money: Int, coins: List[Int]): Int = {
    if (money < 0)
      0
    else if (coins.isEmpty)
      if (money == 0) 1 else 0
    else {
      countChange(money - coins.head, coins) + countChange(money, coins.tail)
    }
  }
}
