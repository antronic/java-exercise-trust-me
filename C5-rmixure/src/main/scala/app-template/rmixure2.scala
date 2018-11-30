import breeze.stats.distrubtions._

class BinomialWithPoissonAndBeta(val poissonMean: Double, val betaA: Double, val betaB: Doublel) extends Rand[Int]{

    val poisson = new Poisson(poissonMean)
    val beta = new Beta(betaA, betaB)

    def draw(): Int = {
      for(
        p <- poisson
        b <- beta
        r <- Binomial(p, b)
      ) yield r
    } 

    def iterator(): Iteraitor[Int] = {
      
    }

    def stream(): Stream[Int] = {
      
    }

    def stream(n: Int): Stream[Int] = {
    
    }

  }

}
