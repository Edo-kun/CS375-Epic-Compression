// Authors:  Dale Skrien & U of Illinois CS faculty

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

/**
 * This class computes a set of all primes smaller than a given limit.
 * <p/>
 * The algorithm is uses Java's Fork/Join framework to compute the primes,
 * but does it in a foolish way.
 */
public class PrimesFJSimple extends PrimesFinder
{

    public Boolean[] computePrimes(int upto) {
        Boolean[] results = new Boolean[upto];

        ForkJoinPool pool = new ForkJoinPool();
        pool.invoke(new PrimeSimpleAction(0, upto, results));
        return results;
    }

    class PrimeSimpleAction extends RecursiveAction
    {
        private int low, high;
        private Boolean[] results;

        public PrimeSimpleAction(int l, int h, Boolean[] r) {
            low = l;
            high = h;
            results = r;
        }

        @Override
        protected void compute() {
            for (int x = low; x < high; x++)
                results[x] = isPrime(x);
        }
    }
}