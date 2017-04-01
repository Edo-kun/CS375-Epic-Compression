// Authors:  Dale Skrien & U of Illinois CS faculty

import java.util.stream.IntStream;

/**
 * A class for demonstrating how to use streams to parallelize a computation.
 */
public class PrimesStreams extends PrimesFinder
{
    @Override
    public Boolean[] computePrimes(int upto) {
        return IntStream.range(0, upto).parallel().mapToObj(this::isPrime).toArray(s ->
                new Boolean[upto]);

    }
}
