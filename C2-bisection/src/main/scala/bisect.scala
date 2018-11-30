/*
bisect.scala

 */

object Bisect {


  def findRoot(low: Double, high: Double)(f: Double => Double): Double = {
    val c = (low + high) / 2
    val fc = f(c)
    if (fc == 0) c
    else if (f(low) * fc < 0) findRoot(low, c)(f)
    else findRoot(c, high)(f)
  }


}

/* eof */

