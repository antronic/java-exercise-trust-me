object Monad {
  
  def weightedAverage(a: Array[Double], w: Array[Double]): Double = {
    val z = a.zip(w).map( x => x._1 * x._2).sum / w.sum
    z
  }

  def concatenate(c: Vector[Char]): String = {
    c.map( ci => ci.toString ).reduce(_ + _)     
  }

}
