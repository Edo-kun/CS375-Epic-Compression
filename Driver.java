// Authors:  Dale Skrien & U of Illinois CS faculty

/**
 * A driver for testing subclasses of PrimesFinder that find primes.
 */

public class Driver
{
    final static int UP_TO = 3500000;
    static long sequentialRuntime = 0;
    final static int NUM_PROCESSORS = Runtime.getRuntime().availableProcessors();

    public static void main(String[] args) throws Exception {
        System.out.println("Number of processors: " + NUM_PROCESSORS);
        test("Sequential version", new PrimesSequential());
        test("Threads version", new PrimesThreads());
        test("Fork-join Simple version", new PrimesFJSimple());
        test("Fork-join version", new PrimesForkJoin());
        test("Parallel streams version", new PrimesStreams());
    }

    private static void test(String version, PrimesFinder p) throws Exception {

        // warm-up
        p.computePrimes(UP_TO);
        p.computePrimes(UP_TO);

        // compute the primes
        Timer.start();
        Boolean[] results = p.computePrimes(UP_TO);
        Timer.stop();

        // output the results
        System.out.println("--------" + version + "----------");

        // output the number of primes found
        long numberOfPrimes = 0;
        for (boolean isP : results)
            if (isP) {
                numberOfPrimes++;
            }
        System.out.println("Number of primes: " + numberOfPrimes);

        // output the time needed to find the primes
        System.out.println("Time: " + Timer.getRuntime() + "ms");

        // output the speedup
        if (sequentialRuntime == 0) {
            sequentialRuntime = Timer.getRuntime(); //sequential time
        }
        else {
            System.out.printf("Speed-up: %.2f\n", sequentialRuntime / 1.0 / Timer
                    .getRuntime());
        }
        System.out.println();
    }
}