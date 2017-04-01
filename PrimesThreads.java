/**
 * This class computes a set of all primes smaller than a given limit.
 * <p>
 * The algorithm uses one thread per available processor to compute
 * the primes concurrently.
 */
public class PrimesThreads extends PrimesFinder
{
    public static final int NUM_THREADS = Runtime.getRuntime().availableProcessors();

    public Boolean[] computePrimes(int upto) {
        Boolean[] results = new Boolean[upto];

        //create an array of threads and start them running
        PrimeThread[] threads = new PrimeThread[NUM_THREADS];
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new PrimeThread(results, upto / NUM_THREADS * i, upto /
                    NUM_THREADS * (i + 1));
            threads[i].start();
        }

        //wait for all threads to finish
        for (PrimeThread t : threads) {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return results;
    }

    class PrimeThread extends Thread
    {
        private Boolean[] results;
        private int start, end;

        public PrimeThread(Boolean[] r, int i, int j) {
            results = r;
            start = i;
            end = j;
        }

        @Override
        public void run() {
            for (int x = start; x < end; x++)
                results[x] = isPrime(x);
        }

    }
}