/*
 * File: Test.java
 * Author: djskrien
 * Date: 3/28/17
 */

import java.util.Arrays;

public class StreamTest
{
    public static void main(String[] args) {
        int[] a = {1,2,3,4,5,6,7,8};
        Arrays.stream(a).map(x -> x*x).filter(x -> x%2 == 0).forEach(x -> System.out
                .println(x));
    }
}
