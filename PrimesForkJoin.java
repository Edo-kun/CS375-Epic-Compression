// Authors:  Dale Skrien & U of Illinois CS faculty

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

/**
 * This class computes a set of all primes smaller than a given limit.
 * <p>
 * The algorithm is uses Java's Fork/Join framework to compute the primes.
 */
public class PrimesForkJoin extends PrimesFinder
{

    public Boolean[] computePrimes(int upto) {
        Boolean[] results = new Boolean[upto];
        ForkJoinPool pool = new ForkJoinPool();
        pool.invoke(new PrimeAction(0, upto, results));
        return results;
    }

    class PrimeAction extends RecursiveAction
    {
        private static final int THRESHOLD = 100;
        private int low, high;
        private Boolean[] results;

        public PrimeAction(int l, int h, Boolean[] r) {
            low = l;
            high = h;
            results = r;
        }

        @Override
        protected void compute() {
            if (high - low <= THRESHOLD) // do it sequentially myself
            {
                for (int x = low; x < high; x++)
                    results[x] = isPrime(x);
            }
            else { // fork the work into two tasks for other threads
                PrimeAction left = new PrimeAction(low, (low + high) / 2, results);
                PrimeAction right = new PrimeAction((low + high) / 2, high, results);
                invokeAll(left, right);
            }
        }
    }
}
