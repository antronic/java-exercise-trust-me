object Fibonacci{
  
  def fib1(f0: Int, f1: Int, n: Int): Int = {
    n match{
      case 0 => f0
      case 1 => f1
      case _ => {
        var i = 1
        var fi_2 = 0
        var fi_1 = f0
        var fi = f1
        while (i < n){
          i += 1
          fi_2 = fi_1
          fi_1 = fi
          fi = fi_1 + fi_2
        }
        fi
      }
    }
  }

  def fib2(f0: Int, f1: Int, n: Int): Int = {
    val fi = if (n == 0 ){
        f0
      }else if (n == 1){
        f1
      }else{
        fib2(f0,f1,n-1) + fib2(f0,f1,n-2)
      }
    fi
  }

  @annotate.tailrec
  def fib3(f0: Int, f1: Int, n: Int): Int = {
      if (n == 0){
        f0
      }else if (n == 1){
        f1
      }else{
        fib2(f1,f0 + f1, n - 1)
      }
  }

  def fib4(f0: Int, f1: Int, n: Int): Int ={
    
    def fib(f0: Int, f1: Int): Stream[Int] = {
      f0 #:: fib(f1,f0+f1)
    }

    val s = fib(f0, f1)
    s(n)
  }

  def fib4a(f0: Int, f1: Int, n: Int): Int = {
    val s: Stream[Int] = f0 #:: s.zip(s.tail).map(f => f._1 + f._2)

    s(n)
  }

}
