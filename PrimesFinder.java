// Authors:  Dale Skrien & U of Illinois CS faculty

/**
 * An abstract class whose children compute which integers are primes.
 */
public abstract class PrimesFinder
{

    /**
     * computes whether x is a prime
     *
     * @param x the integer to test for primality
     * @return true if x is a prime
     */
    public boolean isPrime(int x) {
        if (x == 2) {
            return true;
        }
        if (x < 2 || x % 2 == 0) {
            return false;
        }
        long limit = (long) Math.sqrt(x);
        for (long i = 3; i <= limit; i += 2)
            if (x % i == 0) {
                return false;
            }
        return true;
    }

    /**
     * compute whether each integer in the range 0 to upto is a prime.
     *
     * @param upto the upper limit of integers to test
     * @return a boolean array A of length upto where A[i] = true means
     * that integer i is a prime.
     */
    public abstract Boolean[] computePrimes(int upto);

}
