import breeze.stats.distributions._
import breeze.linalg._

object myapp{

    def main(args: Array[String]): Unit = {
        val v: Array[Double] = Array.fill(5)(Uniform(0, 1).sample)
        val d: DenseVector[Double] = arrayToDenseVector(v)
        val a: Array[Double] = denseVectorToArray(d)
        println(v)
        println(d)
        println(a)
    }
    
    def arrayToDenseVector(a: Array[Double]): DenseVector[Double] = {
        new DenseVector[Double](a)
    }

    def denseVectorToArray(v: DenseVector[Double]): Array[Double] ={
        v.toArray
    }

    def arrayToDenseMatrix(aa: Array[Array[Double]]): DenseMatrix[Double] = {
        DenseMatrix(aa:_*)
    }

    def denseMatrixToArray(m: DenseMatrix[Double]): Array[Array[Double]] = {
        (m(*,::).map(i => i.toArray)).toArray
    }

    def exerciseMatrix(dm:DenseMatrix[Double]): DenseMatrix[Double] = {
        val mj = sum(dm(*,::)) /:/ m.cols.toDouble
        val mi = sum(dm(::,*)) /:/ m.rows.toDouble
        val mm = sum(dm)
        dm(*,::) :-= mi.t
        dm(::,*) :-= mj
        dm :+= mm
        dm
    }
}


