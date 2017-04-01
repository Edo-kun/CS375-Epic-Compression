// Authors:  Dale Skrien & U of Illinois CS faculty

/**
 * This class computes a set of all primes smaller than a given limit.
 * <p>
 * The algorithm is naive, checking each number sequentially to see
 * whether it is divisible by 2 or any odd number smaller than its square root.
 */

public class PrimesSequential extends PrimesFinder
{

    public Boolean[] computePrimes(int upto) {
        Boolean[] results = new Boolean[upto];
        for (int x = 0; x < results.length; x++)
            results[x] = isPrime(x);
        return results;
    }

    public static void main(String[] args) {
        Boolean[] primes = new PrimesSequential().computePrimes(100);
        for (int i = 0; i < primes.length; i++) {
            if (primes[i] == true) {
                System.out.print(i + ", ");
            }
        }

    }
}