
import GLM._

import breeze.linalg._
//import breeze.linalg._
import breeze.numerics._
//import breeze.numerics._
import breeze.stats.distributions.{Gaussian,Poisson}
//import breeze.stats.distributions.{Gaussian, Poisson}

object PoiReg extends App {

val N = 2000
//N: Int = 2000
val beta = DenseVector(-3.0,0.1)
//beta: breeze.linalg.DenseVector[Double] = DenseVector(-3.0, 0.1)
val ones = DenseVector.ones[Double](N)
//ones: breeze.linalg.DenseVector[Double] = DenseVector(1.0,1.0,...
val x = DenseVector(Gaussian(50.0,10.0).sample(N).toArray)
//x: breeze.linalg.DenseVector[Double] = DenseVector(57.470401333447626, ...
val X = DenseMatrix.vertcat(ones.toDenseMatrix, x.toDenseMatrix).t
//X: breeze.linalg.DenseMatrix[double] 
//1.0 57.470401333447626
//1.0 51.87227699389494
//1.0 51.94965234826458
//...
val theta = X * beta
//theta: breeze.linalg.DenseVector[Double] = DenseVector(2.747040133344763, ...
val mu = exp(theta)
//mu: breeze.linalg.DenseVector[Double] = DenseVector(15.596400233187346, ...
val y = mu map (mui => new Poisson(mui).draw) map (_.toDouble)
//y: breeze.linalg.DenseVector[Double] = DenseVector(14.0, 9.0, 6.0, ...


val betahat = poiReg(y,X,its=100)
//betahat: breeze.linalg.DenseVector[Double] =
// DenseVector(-2.9829989727876494, 0.0995945111252215)


import breeze.plot._
//import breeze.plot._
val fig = Figure("Poisson regression")
//fig: breeze.plot.Figure = breeze.plot.Figure@31b3537b
fig.width = 1000
//fig.width: Int = 1000
fig.height = 800
//fig.height: Int = 800
var p = fig.subplot(1,1,0)
//p: breeze.plot.Plot = breeze.plot.Plot@54a23ced
p += plot(x,y,'+')
//res0: breeze.plot.Plot = breeze.plot.Plot@54a23ced
p += plot(x,x map (xi => math.exp( betahat(0)+betahat(1)*xi)),
'.',colorcode="red")
//res1: breeze.plot.Plot = breeze.plot.Plot@54a23ced
p.xlabel = "x"
//p.xlabel: String = x
p.ylabel = "y"
//p.ylabel: String = y
p.title = "Poisson regression"
//p.title: String = Poisson regression

} // PoiReg 
