package calculator

import calculator.Calculator.eval

enum Expr:
  case Literal(v: Double)
  case Ref(name: String)
  case Plus(a: Expr, b: Expr)
  case Minus(a: Expr, b: Expr)
  case Times(a: Expr, b: Expr)
  case Divide(a: Expr, b: Expr)

object Calculator extends CalculatorInterface:
 import Expr.*

  def computeValues(
      namedExpressions: Map[String, Signal[Expr]]): Map[String, Signal[Double]] = {
    namedExpressions.iterator.map {
      mapEntry =>
        val k = mapEntry._1
        val v = mapEntry._2
        val signal = Signal {
          val expr = v()
          eval(expr, namedExpressions)
        }
        (k, signal)
    }.toMap
  }

  def eval(expr: Expr, references: Map[String, Signal[Expr]])(using Signal.Caller): Double = {
    def helper(expr: Expr, references: Map[String, Signal[Expr]], visited: Set[String])(using Signal.Caller): Double = {
      expr match {
        case Expr.Literal(v) => v
        case Expr.Ref(name) =>
          if (visited.contains(name)) {
            // cyclic dependency
            Double.NaN
          } else {
            val refExpr = getReferenceExpr(name, references)
            helper(refExpr, references, visited + name)
          }
        case Expr.Plus(a, b) => helper(a, references, visited) + helper(b, references, visited)
        case Expr.Minus(a, b) => helper(a, references, visited) - helper(b, references, visited)
        case Expr.Times(a, b) => helper(a, references, visited) * helper(b, references, visited)
        case Expr.Divide(a, b) => helper(a, references, visited) / helper(b, references, visited)
      }
    }
    helper(expr, references, visited = Set.empty)
  }

  /** Get the Expr for a referenced variables.
   *  If the variable is not known, returns a literal NaN.
   */
  private def getReferenceExpr(name: String,
      references: Map[String, Signal[Expr]])(using Signal.Caller): Expr =
    references.get(name).fold[Expr] {
      Literal(Double.NaN)
    } { exprSignal =>
      exprSignal()
    }
