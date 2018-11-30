import breeze.stats.distributions._

object Rmixure{

  def main(args: Array[String]): Unit = {
    1.to(20).map(i => println(simpleMonte))
  }

  def simpleMonte(): Double = {
    val poi = new Poisson(20.0)
    val p = poi.sample()
    val beta = new Beta(4, 4)
    val n = beta.sample()
    val bino = new Binomial(p, n)
    val mean = bino.sample(10000).sum / 10000
    mean
  }

}
