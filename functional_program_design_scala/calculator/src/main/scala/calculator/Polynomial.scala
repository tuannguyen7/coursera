package calculator

object Polynomial extends PolynomialInterface:
  def computeDelta(a: Signal[Double], b: Signal[Double],
      c: Signal[Double]): Signal[Double] = {
    Signal {
      val aVal = a()
      val bVal = b()
      val cVal = c()
      bVal*bVal - 4*aVal*cVal
    }
  }


  def computeSolutions(a: Signal[Double], b: Signal[Double],
      c: Signal[Double], delta: Signal[Double]): Signal[Set[Double]] = {
    Signal {
      val aVal = a()
      val bVal = b()
      val cVal = c()
      val delta = computeDelta(Signal(aVal), Signal(bVal), Signal(cVal))()
      if (delta < 0) {
        Set.empty
      } else {
        val rootDelta = Math.sqrt(delta)
        val sol1 = (bVal*(-1) + rootDelta)/(2*aVal)
        val sol2 = (bVal*(-1) - rootDelta)/(2*aVal)
        Set(sol1, sol2)
      }
    }
  }
