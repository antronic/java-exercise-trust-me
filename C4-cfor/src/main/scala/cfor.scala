import breeze.linalg._
import breeze.stats.distributions._
import java.util.concurrent.ThreadLocalRandom
import scala.math.exp

object cfor{

    def main(args: Array[String]):Unit = {
        val a = MarkovChain.metropolisHastings(0.0,(x: Double) => Uniform(x - 0.5, x + 0.5))(x => Gaussian(0,1).logPdf(x)).steps.drop(10).take(10).toArray
        a.foreach(println)
    }

    def rng = ThreadLocalRandom.current()

    def mc(its: Long):Double = {
        @annotation.tailrec
        def sum(its: Long, acc:Double):Double = {
            if(its == 0) acc
            else{
                val u = rng.nextDouble()
                sum(its - 1, acc + exp(-u * u))
            }
        }
        sum(its, 0.0)/its
    }

    def mcp(its: Long, np: Int = 4): Double = {
        (1 to np).par.map(i => mc(its/np)).sum/np
    }

    def time[A](f: => A) = {
        val s = System.nanoTime
        val ret = f
        println("time: "+(System.nanoTime-s)/1e6+"ms")
        ret
    }

    //dense vector
    def metrop(n: Int = 1000, eps: Double = 0.5): DenseVector[Double] = {
        val vec = DenseVector.fill(n)(0.0)
        var x = 0.0
        var oldll = Gaussian(0.0, 1.0).logPdf(x)

        vec(0) = x
        (1 until n).foreach{ i =>
            val can = x + Uniform(-eps, eps).draw
            val loglik = Gaussian(0.0,1.0).logPdf(can)
            val loga = loglik - oldll
            if (math.log(Uniform(0.0, 1.0).draw) < loga){
                x = can
                oldll = loglik
            }
            vec(i) = x
        }
        vec
    }

    //println
    def metrop2(n: Int = 1000, eps: Double = 0.5): Unit = {
        var x = 0.0
        var oldll = Gaussian(0.0, 1.0).logPdf(x)
        (1 to n).foreach{i =>
            val can = x + Uniform(-eps, eps).draw
            val loglik = Gaussian(0, 0).logPdf(can)
            val loga = loglik - oldll
            if (math.log(Uniform(0, 1).draw) < loga){
                x = can
                oldll = loglik
            }
            println(x)
        }
    }

    //tailrec println
    @annotation.tailrec
    def metrop3(n: Int = 1000, eps: Double = 0.5, x: Double = 0.0, oldll: Double = Double.MinValue):Unit = {
        if(n > 0){
            println(x)
            val can = x + Uniform(-eps, eps).draw
            val loglik = Gaussian(0, 1).logPdf(can)
            val loga = loglik - oldll
            if (math.log(Uniform(0, 1).draw) < loga){
                metrop3(n - 1, eps, can, loglik)
            }else{
                metrop3(n - 1, eps, x, oldll)
            }
        }
    }

    //tailrec densevector
    def metrop4(n: Int = 1000, eps: Double = 0.5, x: Double = 0.0, oldll: Double = Double.MinValue, acc: List[Double] = Nil): DenseVector[Double] = {
        if(n == 0){
            DenseVector(acc.reverse.toArray)
        }else{
            val can = x + Uniform(-eps, eps).draw
            val loglik = Gaussian(0,1).logPdf(can)
            val loga = loglik - oldll
            if(math.log(Uniform(0, 1).draw) < loga){
                metrop4(n - 1, eps, can, loglik, can :: acc)
            }else{
                metrop4(n - 1, eps, x, oldll, x :: acc)
            }
        }
    }

    //factor out
    def newState(x: Double, oldll: Double, eps: Double): (Double, Double) = {
        val can = x + Uniform(-eps, eps).draw
        val loglik = Gaussian(0, 1).logPdf(can)
        val loga = loglik - oldll
        if(math.log(Uniform(0, 1).draw) < loga){
            (can, loglik)
        }else{
            (x, oldll)
        }
    }

    //apply with newState (print)
    def metrop5(n: Int = 1000, eps: Double = 0.5, x: Double = 0.0, oldll: Double = Double.MinValue): Unit = {
        if(n > 0){
            println(x)
            val ns = newState(x, oldll, eps)
            metrop5(n - 1, eps, ns._1, ns._2)
        }
    }

    def metrop5b(n: Int = 1000, eps: Double = 0.5, x: Double = 0.0, oldll: Double = Double.MinValue): Unit ={
        if(n > 0){
            println(x)
            val (a, b) = newState(x, oldll, eps)
            metrop5b(n - 1, eps, a, b)
        }
    }

    def metrop6(n: Int = 1000, eps: Double = 0.5, x: Double = 0.0, oldll: Double = Double.MinValue, acc: List[Double] = Nil): DenseVector[Double] = {
        if(n == 0){
            DenseVector(acc.reverse.toArray)
        }else{
            val ns = newState(x, oldll, eps)
            metrop6(n - 1, eps, ns._1, ns._2, ns._1 :: acc)
        }
    }

    //stream
    def nextState(eps: Double)(state: (Double, Double)): (Double, Double) = {
        val (x, oldll) = state
        val can = x + Uniform(-eps, eps).draw
        val loglik = Gaussian(0,1).logPdf(can)
        val loga = loglik - oldll
        if(math.log(Uniform(0, 1).draw) < loga){
            (can, loglik)
        }else{
            (x, oldll)
        }
    }

    def metrop7(eps:Double = 0.5, x: Double = 0.0, oldll: Double = Double.MinValue): Stream[Double] = {
        Stream.iterate((x, oldll))(nextState(eps)).map(_._1)
    }

    //super pure
    def kernel(x: Double): Rand[Double] = for {
        innov <- Uniform(-0.5, 0.5)
        can = x + innov
        oldll = Gaussian(0.0, 1.0).logPdf(x)
        loglik = Gaussian(0.0, 1.0).logPdf(can)
        loga = loglik - oldll
        u <- Uniform(0.0,1.0)
    } yield if (math.log(u) < loga) can else x
}