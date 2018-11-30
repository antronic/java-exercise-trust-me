
import GLM._

import breeze.linalg._
// import breeze.linalg._
import breeze.stats.distributions.{Gaussian,Binomial}
// import distributions.{Gaussian, Binomial}

object LogReg extends App {

val N = 2000
// N: Int = 2000
val beta = DenseVector(0.1,0.3)
// beta: DenseVector[Double] = DenseVector(0.1, 0.3)
val ones = DenseVector.ones[Double](N)
// ones: DenseVector[Double] = DenseVector(1.0, ...
val x = DenseVector(Gaussian(1.0,3.0).sample(N).
  toArray)
// x: DenseVector[Double] = DenseVector(6.272108997,
//  3.0135444386214765, 4.373649007468049, ...
val X = DenseMatrix.vertcat(ones.toDenseMatrix,
  x.toDenseMatrix).t
// X: breeze.linalg.DenseMatrix[Double] =
// 1.0  6.27210899796815
// 1.0  3.0135444386214765
// 1.0  4.373649007468049
// 1.0  -0.34689004119207434
// ...
val theta = X * beta
// theta: DenseVector[Double] = DenseVector(
// 1.981632699390445,
// 1.004063331586443, 1.4120947022404147, ...
def expit(x: Double): Double = 1.0/(1.0+math.exp(-x))
// expit: (x: Double)Double
val pr = theta map expit
// pr: breeze.linalg.DenseVector[Double] = DenseVector(
//  0.8788551012256975, 0.7318567276541773, ...
val y = pr map (pi => new Binomial(1,pi).draw) map (
   _.toDouble)
// y: DenseVector[Double]=DenseVector(1.0,1.0,1.0,...



val betahat = logReg(y,X)
// betahat: DenseVector[Double] = DenseVector(
//   0.0563791369276622, 0.31606883046872236)


import breeze.plot._
// import breeze.plot._
val fig = Figure("Logistic regression")
// fig: Figure = breeze.plot.Figure@11befd2e
fig.width = 1000
// fig.width: Int = 1000
fig.height = 800
// fig.height: Int = 800
var p = fig.subplot(1,1,0)
// p: breeze.plot.Plot = breeze.plot.Plot@62b33d65
p += plot(x,y,'+')
// res0: breeze.plot.Plot = breeze.plot.Plot@62b33d65
p += plot(x,x map (xi =>
  expit(betahat(0)+betahat(1)*xi)),
  '.',colorcode="red")
// res1: breeze.plot.Plot = breeze.plot.Plot@62b33d65
p.xlabel = "x"
// p.xlabel: String = x
p.ylabel = "y"
// p.ylabel: String = y
p.title = "Logistic regression"
// p.title: String = Logistic regression


} // LogReg
