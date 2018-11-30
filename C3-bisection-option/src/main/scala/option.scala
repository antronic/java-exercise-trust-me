/*
option.scala

 */

object OptionBisect {

  // Part A

  def findRootOpt(low: Double, high: Double)(f: Double => Double): Option[Double] = {
    val flow = f(low)
    val fhigh = f(high)
    if (flow * fhigh >= 0) None
    val mid = (low + high) / 2
    val fmid = f(mid)
    if(fmid == 0) mid
    else if(flow * fmid < 0) findRootOpt(low, mid)(f)
    else findRootOpt(mid, high)(f)
  }


  // Part B

  //def solveQuad(a: Double): Option[Double] = ???


}

/* eof */

